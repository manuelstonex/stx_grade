#include <jni.h>
#include <android/log.h>

#ifndef __VAN_SYS_MCU_H
#define __VAN_SYS_MCU_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <set>
#include <map>
#include <vector>
#include <string>

using namespace std;


typedef unsigned char byte;

#ifdef __cplusplus
extern "C" {
#endif

void onCallback(int type, const byte* pData, int length);

#ifdef __cplusplus
}
#endif

#endif