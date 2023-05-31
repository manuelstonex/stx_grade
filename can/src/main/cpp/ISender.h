#pragma once
#include "stdafx.h"

class ISender
{
public:
	virtual int IsConnected() = 0;
	virtual int Send(const byte* pBuf, int length) = 0;
};
