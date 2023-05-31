#pragma once
#include "stdafx.h"
#include <stdint.h>
#include <vector>

class Utils
{
public:
	static byte* WriteInt(byte* pBuf, int value, int bytes);
	static int ReadInt(const byte* pBuf, int bytes);
	static byte CRC8(const byte* pBuf, int length);
};
