#pragma once

class IOnRecv
{
public:
	virtual void OnRecv(const byte* pBuf, int length) = 0;
};

