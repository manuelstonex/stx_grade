#include "stdafx.h"
#include "PacketParser.h"
#include "Utils.h"

PacketParser::PacketParser()
{
	m_pOnPacket = NULL;
	m_pBuffer = new byte[4000];

	Reset();
}

PacketParser::~PacketParser()
{

}

void PacketParser::SetOnPacket(IOnPacket* pOnPacket)
{
	m_pOnPacket = pOnPacket;
}

void PacketParser::Reset()
{
	m_nLength = 0;
}

void PacketParser::Parse(const byte* pBuf, int length)
{
	memcpy(m_pBuffer + m_nLength, pBuf, length);
	m_nLength += length;

	while (m_nLength >= 7)
	{
		if (!ParsePack()) {
			break;
		}
	}
}

bool PacketParser::ParsePack()
{
	int nl = m_nLength - 7;

	for (int i = 0; i <= m_nLength - 7; ++i)
	{
		if (m_pBuffer[i] == 2 && m_pBuffer[i + 1] == 91)
		{
			int bs = m_pBuffer[i + 3];
			int t1 = i + bs + 7;
			if (t1 > m_nLength) {
				++i;
				continue;
			}

			if (m_pBuffer[t1 - 2] != 93 || m_pBuffer[t1 - 1] != 3) {
				++i;
				continue;
			}

			Packet* pPacket = new Packet(m_pBuffer[i + 2]);
			pPacket->SetBody(m_pBuffer + i + 5, m_pBuffer[i + 3]);

			if (NULL != m_pOnPacket) {
				m_pOnPacket->OnPacket(pPacket);
			}

			memmove(m_pBuffer, m_pBuffer + t1, m_nLength - t1);
			m_nLength -= t1;

			return true;
		}
	}
	
	return false;
}
