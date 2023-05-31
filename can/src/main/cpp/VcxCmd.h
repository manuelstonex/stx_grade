#pragma once
#include "stdafx.h"
#include "VcxCore.h"


class VcxCmd : public VcxCore
{
private:
	VcxCmd();
	~VcxCmd();
public:
	static VcxCmd* Instance();
public:
	string getVersion();
	int getAccState();
    bool PowerCtrl(int val);
	int getPowerVoltage();
	int getTemperature();
    int getCanCount();
	int getCanSpeed(int channel);
	bool setCanSpeed(int channel, int value);
    bool CanWrite(int channel, int id, const byte* pData, int length);
    bool CanFilterCtrl(int channel, int type);
	bool CanHwFilterAdd(int channel, uint32_t id, uint32_t mask);
	bool CanHwFilterClear(int channel);
    bool CanSwFilterAdd(int channel, uint32_t id, uint32_t mask);
    bool CanSwFilterClear(int channel);
    int getBlockCount();
    bool BlockWrite(int index, const byte* pData, int length);
    bool BlockRead(int index, byte* pBuf, int length);
    int getInputCount();
    int InputGet(int index);
    int getOutputCount();
    int OutputGet(int index);
    bool OutputSet(int index, int value);
    bool setCallback(int filter);
	bool UpdateMcu(const char* filepath);
private:
	static VcxCmd* m_pInstance;
};
