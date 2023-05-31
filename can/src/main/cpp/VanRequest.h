#pragma once
#include "stdafx.h"
#include "ISender.h"
#include <semaphore.h>
#include "Packet.h"
#include "Lock.h"


class VanRequest
{
public:
	VanRequest();
	~VanRequest();
	Packet* Request(Packet* pPacket, int timeout);
	void OnRecvPacket(Packet* pPacket);
	void SetSender(ISender* pSender);
private:
	void Notify();
	int  Wait(int timeout);
	void RequestInner(Packet* pPacket, int timeout);
private:
	Packet* m_pRespPacket;
	ISender* m_pSender;
	CLock m_Lock;
	sem_t m_hSem;
	int  m_ReqCmd;
};
