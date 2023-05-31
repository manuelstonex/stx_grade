#pragma once
#include "IOnPacket.h"

class PacketParser
{
public:
	PacketParser();
	~PacketParser();
	void SetOnPacket(IOnPacket* pOnPacket);
	void Parse(const byte* pBuf, int length);
	void Reset();
private:
	bool ParsePack();
private:
	byte*  m_pBuffer;
	int    m_nLength;
	IOnPacket* m_pOnPacket;
};
