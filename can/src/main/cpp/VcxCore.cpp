#include "stdafx.h"
#include "VcxCore.h"
#include <time.h>
#include <errno.h>
#include <signal.h>
#include "Utils.h"


VcxCore::VcxCore()
{
	m_TcpClient.SetOnRecv(this);
	m_Parser.SetOnPacket(this);
	m_Request.SetSender(this);
}

VcxCore::~VcxCore()
{
}

int VcxCore::IsConnected()
{
	return m_TcpClient.IsConnected();
}

int VcxCore::Send(const byte* pBuf, int length)
{
	return m_TcpClient.Send(pBuf, length);
}

void VcxCore::OnRecv(const byte* pBuf, int length)
{
	m_Parser.Parse(pBuf, length);
}

void VcxCore::OnPacket(Packet* pPacket)
{
	int nCmd = pPacket->GetCMD();
	if (nCmd < 0x80) 
	{
		m_Handler.Handle(pPacket);
	}
	else
	{
		m_Request.OnRecvPacket(pPacket);
	}
}
