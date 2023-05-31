#include "stdafx.h"
#include "VanException.h"

VanException::VanException(int id, const char* pMsg)
	: m_ID(id)
{
	if (NULL == pMsg) {
		InitMsg();
	} else {
		m_Msg = pMsg;
	}
}

VanException::~VanException()
{
}

int VanException::GetID() const
{
	return m_ID;
}

string VanException::GetMsg() const
{
	return m_Msg;
}

void VanException::InitMsg()
{

}
