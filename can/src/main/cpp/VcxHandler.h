#pragma once
#include "Packet.h"

class VcxHandler
{
public:
	VcxHandler();
	virtual ~VcxHandler();
	void Handle(Packet* pPacket);
protected:
    void OnAcc(const byte* pData, int length);
    void OnInput(const byte* pData, int length);
    void CanRead(const byte* pData, int length);
};
