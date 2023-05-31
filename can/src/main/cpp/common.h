#ifndef __VAN_COMMON_H
#define __VAN_COMMON_H


enum PacketCmdID {
	PCID_GET_VERSION            = 0x03,	//
	PCID_ACC_GET                = 0x04,	//
	PCID_ACC_PUSH               = 0x05,	//
	PCID_POWER_CTRL             = 0x06,	//
	PCID_GET_POWER_VOLTAGE      = 0x07,	//
	PCID_GET_TEMPERATURE		= 0x08,	//
	PCID_CAN_COUNT				= 0x0F,	//
	PCID_CAN_GET_BAUDRATE       = 0x10,	//
	PCID_CAN_SET_BAUDRATE       = 0x11,	//
	PCID_CAN_WRITE              = 0x12,	//
	PCID_CAN_READ               = 0x13,	//
	PCID_CAN_FILTER_CTRL        = 0x14,	//
	PCID_CAN_HW_FILTER_ADD      = 0x15,	//
	PCID_CAN_HW_FILTER_CLEAR    = 0x16,	//
	PCID_CAN_SW_FILTER_ADD      = 0x17,	//
	PCID_CAN_SW_FILTER_CLEAR    = 0x18,	//
	PCID_BLOCK_COUNT            = 0x19,	//
	PCID_BLOCK_WRITE            = 0x1A,	//
	PCID_BLOCK_READ             = 0x1B,	//
	PCID_INPUT_COUNT            = 0x1C,	//
	PCID_INPUT_GET              = 0x1D,	//
	PCID_INPUT_PUSH             = 0x1E,	//
	PCID_OUTPUT_COUNT           = 0x1F,	//
	PCID_OUTPUT_GET             = 0x20,	//
	PCID_OUTPUT_SET             = 0x21,	//
    PCID_UPDATE_MCU         	= 0x22, //

    PCID_CALLBACK_SET           = 0x40,	//
};

#endif  // #ifndef __VAN_COMMON_H
