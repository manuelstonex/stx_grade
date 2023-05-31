#include "stdafx.h"
#include "VcxHandler.h"
#include "common.h"

VcxHandler::VcxHandler()
{
}

VcxHandler::~VcxHandler()
{
}

void VcxHandler::Handle(Packet* pPacket)
{
    int nCmd = pPacket->GetCMD();
    const byte* pBody = pPacket->GetBody();

    switch (nCmd)
    {
        case PCID_ACC_PUSH:
            OnAcc(pBody, 1);
            break;
        case PCID_INPUT_PUSH:
            OnInput(pBody, pPacket->GetBodySize());
            break;
        case PCID_CAN_READ:
            CanRead(pBody, pPacket->GetBodySize());
            break;
    }

    delete pPacket;
}

void VcxHandler::OnAcc(const byte* pData, int length)
{
    onCallback(1, pData, length);
}

void VcxHandler::OnInput(const byte* pData, int length)
{
    onCallback(4, pData, length);
}

void VcxHandler::CanRead(const byte* pData, int length)
{
    onCallback(2, pData, length);
}
