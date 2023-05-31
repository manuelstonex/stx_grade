#include "stdafx.h"
#include <dirent.h>
#include <errno.h>
#include <regex.h>
#include "VcxCmd.h"

#ifdef __cplusplus
extern "C" {
#endif

static VcxCmd* m_pInst = NULL;
static JavaVM* m_jvm = NULL;
static jclass m_class = NULL;
static jmethodID m_mid = NULL;

JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *jvm, void *reserved)
{
    JNIEnv *env;
    if (jvm->GetEnv((void **)&env, JNI_VERSION_1_6)) {
        return JNI_ERR;
    }

    m_jvm = jvm;
    m_pInst = VcxCmd::Instance();

    return JNI_VERSION_1_6;
}

JNIEXPORT jstring JNICALL
Java_com_van_jni_VanMcu_getVersion(JNIEnv *env, jclass cls)
{
    string str = m_pInst->getVersion();
    return env->NewStringUTF(str.c_str());
}

JNIEXPORT jint JNICALL
Java_com_van_jni_VanMcu_getAccState(JNIEnv *env, jclass cls)
{
    return m_pInst->getAccState();
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_PowerCtrl(JNIEnv *env, jclass cls,
                                  jint val)
{
    return m_pInst->PowerCtrl(val);
}

JNIEXPORT jint JNICALL
Java_com_van_jni_VanMcu_getPowerVoltage(JNIEnv *env, jclass cls)
{
    return m_pInst->getPowerVoltage();
}

JNIEXPORT jint JNICALL
Java_com_van_jni_VanMcu_getTemperature(JNIEnv *env, jclass cls)
{
    return m_pInst->getTemperature();
}

JNIEXPORT jint JNICALL
Java_com_van_jni_VanMcu_getCanCount(JNIEnv *env, jclass cls)
{
    return m_pInst->getCanCount();
}

JNIEXPORT jint JNICALL
Java_com_van_jni_VanMcu_getCanSpeed(JNIEnv *env, jclass cls,
                                    jint channel)
{
    return m_pInst->getCanSpeed(channel);
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_setCanSpeed(JNIEnv *env, jclass cls,
                                  jint channel, jint value)
{
    return m_pInst->setCanSpeed(channel, value);
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_CanWrite(JNIEnv *env, jclass cls,
                                 jint channel, jint id, jbyteArray data)
{
    int length = 0;
    jbyte* buf = NULL;

    if (NULL != data) {
        length = env->GetArrayLength(data);
        buf = new jbyte[length];
        env->GetByteArrayRegion(data, 0, length, buf);
    }

    bool bRet = m_pInst->CanWrite(channel, id, (const byte*)buf, length);
    return bRet;
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_CanFilterCtrl(JNIEnv *env, jclass cls,
                                      jint channel, jint type)
{
    return m_pInst->CanFilterCtrl(channel, type);
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_CanHwFilterAdd(JNIEnv *env, jclass cls,
                                       jint channel, jint id, jint mask)
{
    return m_pInst->CanHwFilterAdd(channel, id, mask);
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_CanHwFilterClear(JNIEnv *env, jclass cls,
                                         jint channel)
{
    return m_pInst->CanHwFilterClear(channel);
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_CanSwFilterAdd(JNIEnv *env, jclass cls,
                                       jint channel, jint id, jint mask)
{
    return m_pInst->CanSwFilterAdd(channel, id, mask);
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_CanSwFilterClear(JNIEnv *env, jclass cls,
                                         jint channel)
{
    return m_pInst->CanSwFilterClear(channel);
}

JNIEXPORT jint JNICALL
Java_com_van_jni_VanMcu_getBlockCount(JNIEnv *env, jclass cls)
{
    return m_pInst->getBlockCount();
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_BlockWrite(JNIEnv *env, jclass cls,
                                   jint index, jbyteArray data)
{
    int length = 0;
    jbyte* buf = NULL;

    if (NULL != data) {
        length = env->GetArrayLength(data);
        buf = new jbyte[length];
        env->GetByteArrayRegion(data, 0, length, buf);
    }

    bool bRet = m_pInst->BlockWrite(index, (const byte*)buf, length);
    return bRet;
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_BlockRead(JNIEnv *env, jclass cls,
                                  jint index, jbyteArray jbuf, jint length)
{
    int size = env->GetArrayLength(jbuf);
    if (length > size) {
        return false;
    }

    jbyte* buf = env->GetByteArrayElements(jbuf, NULL);

    bool bRet = m_pInst->BlockRead(index, (byte*)buf, length);
    return bRet;
}

JNIEXPORT jint JNICALL
Java_com_van_jni_VanMcu_getInputCount(JNIEnv *env, jclass cls)
{
    return m_pInst->getInputCount();
}

JNIEXPORT jint JNICALL
Java_com_van_jni_VanMcu_InputGet(JNIEnv *env, jclass cls,
                                      jint index)
{
    return m_pInst->InputGet(index);
}

JNIEXPORT jint JNICALL
Java_com_van_jni_VanMcu_getOutputCount(JNIEnv *env, jclass cls)
{
    return m_pInst->getOutputCount();
}

JNIEXPORT jint JNICALL
Java_com_van_jni_VanMcu_OutputGet(JNIEnv *env, jclass cls,
                                  jint index)
{
    return m_pInst->OutputGet(index);
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_OutputSet(JNIEnv *env, jclass cls,
                                  jint index, jint value)
{
    return m_pInst->OutputSet(index, value);
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_setCallback(JNIEnv *env, jclass cls,
                                  jint filter)
{
    if (NULL == m_class) {
        jclass tmp = env->FindClass("com/van/jni/VanMcu");
        m_class = (jclass)env->NewGlobalRef(tmp);

        m_mid = env->GetStaticMethodID(m_class, "onCallback", "(I[B)V");
    }

    return m_pInst->setCallback(filter);
}

JNIEXPORT jboolean JNICALL
Java_com_van_jni_VanMcu_UpdateFirmware(JNIEnv *env, jclass cls,
                                       jstring path)
{
    const char* szStr = env->GetStringUTFChars(path, NULL);
    bool bRet = m_pInst->UpdateMcu(szStr);

    return bRet;
}

void onCallback(int type, const byte* pData, int length)
{
    if (NULL == m_mid) {
        return;
    }

    JNIEnv *env;
    if (m_jvm->AttachCurrentThread(&env, 0) < 0) {
        return;
    }

    jbyteArray bytes = env->NewByteArray(length);
    env->SetByteArrayRegion(bytes, 0, length, (const jbyte*)pData);

    env->CallStaticVoidMethod(m_class, m_mid, type, bytes);

    m_jvm->DetachCurrentThread();
}

#ifdef __cplusplus
}
#endif
