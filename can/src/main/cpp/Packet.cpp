#include "Packet.h"
#include <string.h>
#include "Utils.h"

Packet::Packet(int cmd)
	: m_nCMD(cmd)
{
	m_nBodySize = 0;
	m_pBodyBuff = NULL;
	m_bBuild = false;
	m_nLength = 0;
	m_pBuffer = NULL;
}

Packet::~Packet()
{
	Reset();
}

void Packet::Reset()
{
	if (NULL != m_pBodyBuff) {
		delete[] m_pBodyBuff;
		m_pBodyBuff = NULL;
	}

	if (NULL != m_pBuffer) {
		delete[] m_pBuffer;
		m_pBuffer = NULL;
	}

	m_nBodySize = 0;
	m_nLength = 0;
	m_bBuild = false;
}

int Packet::GetCMD()
{
	return m_nCMD;
}

int Packet::GetBodySize()
{
	return m_nBodySize;
}

byte* Packet::GetBody()
{
	return m_pBodyBuff;
}

void Packet::SetBody(byte* pBuf, int length)
{
	Reset();

	if (NULL != pBuf && length > 0) {
		m_pBodyBuff = new byte[length];
		memcpy(m_pBodyBuff, pBuf, length);
		m_nBodySize = length;
	}
}

int Packet::GetLength()
{
	return m_nLength;
}

byte* Packet::GetBuffer()
{
	return m_pBuffer;
}

void Packet::Build()
{
	if (m_bBuild) {
		return;
	}

	int length = m_nBodySize + 7;
	byte* pBuf = new byte[length];

	pBuf[0] = 2;
	pBuf[1] = 91;
	pBuf[2] = (byte)m_nCMD;
	pBuf[3] = (byte)m_nBodySize;
	pBuf[4] = Utils::CRC8(pBuf, 4);

	if (m_nBodySize > 0) {
		memcpy(pBuf + 5, m_pBodyBuff, m_nBodySize);
	}

	pBuf[length-2] = 93;
	pBuf[length-1] = 3;

	m_pBuffer = pBuf;
	m_nLength = length;

	m_bBuild = true;
}
