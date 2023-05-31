#pragma once
#include "stdafx.h"

class Packet
{
public:
	Packet(int cmd);
	~Packet();
	void Reset();

	int GetCMD();
	int GetBodySize();
	byte* GetBody();
	void SetBody(byte* pBuf, int length);

	void Build();
	int GetLength();
	byte* GetBuffer();
protected:
	const int m_nCMD;
	int   m_nBodySize;
	byte* m_pBodyBuff;
	byte* m_pBuffer;
	int   m_nLength;
	bool  m_bBuild;
};
