#pragma once
#include "Packet.h"

class IOnPacket
{
public:
	virtual void OnPacket(Packet* pPacket) = 0;
};
