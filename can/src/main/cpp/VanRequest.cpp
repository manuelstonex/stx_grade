#include "stdafx.h"
#include "VanRequest.h"
#include <time.h>
#include <errno.h>
#include <signal.h>
#include "VanException.h"
#include "Utils.h"


VanRequest::VanRequest()
{
	m_pSender = NULL;
	m_pRespPacket = NULL;
	m_ReqCmd = -1;

	sem_init(&m_hSem, 0, 0);
}

VanRequest::~VanRequest()
{
}

void VanRequest::SetSender(ISender* pSender)
{
	m_pSender = pSender;
}

int VanRequest::Wait(int timeout)
{
	struct timespec ts;

    if (clock_gettime(CLOCK_REALTIME, &ts) < 0)
	{
		return -1;
	}

    ts.tv_sec  += timeout/1000;
    ts.tv_nsec += timeout%1000*1000000;

    return sem_timedwait(&m_hSem, &ts);
}

void VanRequest::Notify()
{
}

Packet* VanRequest::Request(Packet* pPacket, int timeout)
{
	if (!m_pSender->IsConnected()) {
		throw new VanException(EC_NOT_CONNECTED);
	}

	m_Lock.Lock();
	VanException* pException = NULL;

	try {
		RequestInner(pPacket, timeout);
	} catch (VanException* e) {
		pException = e;
	}

	m_ReqCmd = -1;
	m_Lock.Unlock();

	if (NULL != pException) {
		throw pException;
	}

	if (NULL!=m_pRespPacket
		&& 0x81==m_pRespPacket->GetCMD()) 
	{
		int result = m_pRespPacket->GetBody()[1];

		m_pRespPacket = NULL;
		int exception = 0;

		switch (result) {
		case 1:
			exception = EC_EXCUTE_FAILED;
			break;
		case 2:
			exception = EC_MSG_ERROR;
			break;
		case 3:
			exception = EC_UNSUPPORTED;
			break;
		}

		if (0 != exception) {
			throw new VanException(exception);
		}
	}

	return m_pRespPacket;
}

void VanRequest::RequestInner(Packet* pPacket, int timeout)
{
	pPacket->Build();
	m_ReqCmd = pPacket->GetCMD();
	m_pRespPacket = NULL;

	if (!m_pSender->Send(pPacket->GetBuffer(), pPacket->GetLength())) {
		throw new VanException(EC_SEND_FAILED);
	}

	if (timeout <= 0) {
		return;
	}

	int result = Wait(timeout);

	if (0 != result) {
		if (ETIMEDOUT == errno) {
			throw new VanException(EC_RESP_TIMEOUT);
		} else {
			throw new VanException(EC_UNKNOWN);
		}
	}
}

void VanRequest::OnRecvPacket(Packet* pPacket)
{
	int nCmd = pPacket->GetCMD();

	if (-1 != m_ReqCmd)
	{
		if ((0x81==nCmd && m_ReqCmd==pPacket->GetBody()[0])
			|| (nCmd-0x80 == m_ReqCmd))
		{
			m_pRespPacket = pPacket;
			sem_post(&m_hSem);
		}
	}
}
