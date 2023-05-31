#include "stdafx.h"
#include "VcxCmd.h"
#include "VanException.h"
//#include "CmdHandler.h"
#include <string.h>
#include "Utils.h"
#include "common.h"

VcxCmd* VcxCmd::m_pInstance = NULL;

VcxCmd::VcxCmd()
{
//	m_pHandler = new CmdHandler();
	m_TcpClient.Open(8210);
}

VcxCmd::~VcxCmd()
{
}

VcxCmd* VcxCmd::Instance()
{
	if (NULL == m_pInstance) {
		m_pInstance = new VcxCmd();
	}

	return m_pInstance;
}

string VcxCmd::getVersion()
{
	Packet packet(PCID_GET_VERSION);

	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return "";
	}

	string str((char*)pResp->GetBody(), pResp->GetBodySize());

	return str;
}

int VcxCmd::getAccState()
{
	Packet packet(PCID_ACC_GET);
	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return -1;
	}

	int value = pResp->GetBody()[0];

	return value;
}

bool VcxCmd::PowerCtrl(int val)
{
	byte data = val;

	Packet packet(PCID_POWER_CTRL);
	packet.SetBody(&data, 1);

	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return false;
	}

	return true;
}

int VcxCmd::getPowerVoltage()
{
	Packet packet(PCID_GET_POWER_VOLTAGE);

	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return 0;
	}

	int value = Utils::ReadInt(pResp->GetBody(), 4);

	return value;
}

int VcxCmd::getTemperature()
{
	Packet packet(PCID_GET_TEMPERATURE);

	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return 0;
	}

	int value = Utils::ReadInt(pResp->GetBody(), 4);
	return value;
}

int VcxCmd::getCanCount()
{
	Packet packet(PCID_CAN_COUNT);

	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return 0;
	}

    int value = pResp->GetBody()[0];

    return value;
}

int VcxCmd::getCanSpeed(int channel)
{
	byte data = channel;

	Packet packet(PCID_CAN_GET_BAUDRATE);
	packet.SetBody(&data, 1);

	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return -1;
	}

	int value = Utils::ReadInt(pResp->GetBody(), 4);

	return value;
}

bool VcxCmd::setCanSpeed(int channel, int value)
{
    byte buf[5];
    buf[0] = channel;
    Utils::WriteInt(buf + 1, value, 4);

    Packet packet(PCID_CAN_SET_BAUDRATE);
    packet.SetBody(buf, 5);

	Packet* pResp = NULL;
	bool bRet = true;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
		bRet = false;
	}

	return bRet;
}

bool VcxCmd::CanWrite(int channel, int id, const byte* pData, int length)
{
	byte buf[14];
	buf[0] = channel;
	Utils::WriteInt(buf + 1, id, 4);
	buf[5] = length;

	int size = 6;
	if (NULL != pData) {
		memcpy(buf + 6, pData, length);
		size += length;
	}

	Packet packet(PCID_CAN_WRITE);
	packet.SetBody(buf, size);

	Packet* pResp = NULL;
	bool bRet = true;

	try {
		pResp = m_Request.Request(&packet, 0);
	} catch (VanException* e) {
		bRet = false;
	}

	return bRet;
}

bool VcxCmd::CanFilterCtrl(int channel, int type)
{
	byte buf[2];
	buf[0] = channel;
	buf[1] = type;

	Packet packet(PCID_CAN_FILTER_CTRL);
	packet.SetBody(buf, 2);

	Packet* pResp = NULL;
	bool bRet = true;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
		bRet = false;
	}
	return bRet;
}

bool VcxCmd::CanHwFilterAdd(int channel, uint32_t id, uint32_t mask)
{
	byte buf[9];
	buf[0] = channel;

	Utils::WriteInt(buf + 1, id, 4);
	Utils::WriteInt(buf + 5, mask, 4);

	Packet packet(PCID_CAN_HW_FILTER_ADD);
	packet.SetBody(buf, 9);

	Packet* pResp = NULL;
	bool bRet = true;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
		bRet = false;
	}

	return bRet;
}

bool VcxCmd::CanHwFilterClear(int channel)
{
	byte data = channel;

	Packet packet(PCID_CAN_HW_FILTER_CLEAR);
	packet.SetBody(&data, 1);

	Packet* pResp = NULL;
	bool bRet = true;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
		bRet = false;
	}

	return bRet;
}

bool VcxCmd::CanSwFilterAdd(int channel, uint32_t id, uint32_t mask)
{
	byte buf[9];
	buf[0] = channel;

	Utils::WriteInt(buf + 1, id, 4);
	Utils::WriteInt(buf + 5, mask, 4);

	Packet packet(PCID_CAN_SW_FILTER_ADD);
	packet.SetBody(buf, 9);

	Packet* pResp = NULL;
	bool bRet = true;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
		bRet = false;
	}

	return bRet;
}

bool VcxCmd::CanSwFilterClear(int channel)
{
	byte data = channel;

	Packet packet(PCID_CAN_SW_FILTER_CLEAR);
	packet.SetBody(&data, 1);

	const Packet* pResp = NULL;
	bool bRet = true;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
		bRet = false;
	}

	return bRet;
}

int VcxCmd::getBlockCount()
{
    Packet packet(PCID_BLOCK_COUNT);

    Packet* pResp = NULL;

    try {
        pResp = m_Request.Request(&packet, 2000);
    } catch (VanException* e) {
    }

    if (NULL == pResp) {
        return 0;
    }

    int value = pResp->GetBody()[0];
    return value;
}

bool VcxCmd::BlockWrite(int index, const byte* pData, int length)
{
	byte* pBody = new byte[length + 2];
	pBody[0] = index;
	pBody[1] = length;
	memcpy(pBody+2, pData, length);

	Packet packet(PCID_BLOCK_WRITE);
	packet.SetBody(pBody, length+2);
	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
		return false;
	}

	return true;
}

bool VcxCmd::BlockRead(int index, byte* pBuf, int length)
{
	byte body[2];
	body[0] = index;
	body[1] = length;

	Packet packet(PCID_BLOCK_READ);
	packet.SetBody(body, 2);

	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return false;
	}

	memcpy(pBuf, pResp->GetBody(), pResp->GetBodySize());
	return true;
}

int VcxCmd::getInputCount()
{
	Packet packet(PCID_INPUT_COUNT);

	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return 0;
	}

	int value = pResp->GetBody()[0];

	return value;
}

int VcxCmd::InputGet(int index)
{
	byte data = index;
	Packet packet(PCID_INPUT_GET);
	packet.SetBody(&data, 1);

	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return -1;
	}

	int value = pResp->GetBody()[0];

	return value;
}

int VcxCmd::getOutputCount()
{
	Packet packet(PCID_OUTPUT_COUNT);

	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return 0;
	}

	int value = pResp->GetBody()[0];

	return value;
}

int VcxCmd::OutputGet(int index)
{
	byte data = index;
	Packet packet(PCID_OUTPUT_GET);
	packet.SetBody(&data, 1);

	Packet* pResp = NULL;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
	}

	if (NULL == pResp) {
		return -1;
	}

	int value = pResp->GetBody()[0];

	return value;
}

bool VcxCmd::OutputSet(int index, int value)
{
	byte buf[2];
	buf[0] = index;
	buf[1] = value;

	Packet packet(PCID_OUTPUT_SET);
	packet.SetBody(buf, 2);

	Packet* pResp = NULL;
	bool bRet = true;

	try {
		pResp = m_Request.Request(&packet, 2000);
	} catch (VanException* e) {
		bRet = false;
	}

	return bRet;
}

bool VcxCmd::setCallback(int filter)
{
    byte data = filter;

    Packet packet(PCID_CALLBACK_SET);
    packet.SetBody(&data, 1);

    Packet* pResp = NULL;
    bool bRet = true;

    try {
        pResp = m_Request.Request(&packet, 2000);
    } catch (VanException* e) {
        bRet = false;
    }

    return bRet;
}

bool VcxCmd::UpdateMcu(const char* filepath)
{
	Packet packet(PCID_UPDATE_MCU);
	packet.SetBody((byte*)filepath, strlen(filepath));

	Packet* pResp = NULL;
	bool bRet = true;

	try {
		pResp = m_Request.Request(&packet, 10000);
	} catch (VanException* e) {
		bRet = false;
	}

	return bRet;
}
