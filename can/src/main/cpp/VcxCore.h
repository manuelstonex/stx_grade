#pragma once
#include "stdafx.h"
#include "TcpClient.h"
#include "PacketParser.h"
#include "VanRequest.h"
#include "VcxHandler.h"
#include "IOnPacket.h"
#include "ISender.h"
#include "IOnRecv.h"

class VcxCore : public ISender, public IOnRecv, public IOnPacket
{
public:
	VcxCore();
	virtual ~VcxCore();
	virtual int IsConnected();
	virtual int Send(const byte* pBuf, int length);
	virtual void OnRecv(const byte* pBuf, int length);
	virtual void OnPacket(Packet* pPacket);
protected:
	TcpClient m_TcpClient;
	PacketParser m_Parser;
	VanRequest m_Request;
	VcxHandler m_Handler;
};
