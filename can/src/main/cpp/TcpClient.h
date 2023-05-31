#pragma once
#include "stdafx.h"
#include "IOnRecv.h"
#include <pthread.h>

class TcpClient
{
public:
	TcpClient();
	virtual ~TcpClient();
	int Open(int nPort);
	void Close();
	int IsConnected();
	int Send(const byte* pData, int nLength);
	int IsRunning();
	void SetOnRecv(IOnRecv* pOnRecv);
private:
	int Connect();
	void Work();
	int BeginThread();
	static void* ThreadProc(void* lpParam);
	void* ThreadFunc();
private:
	int    m_Socket;
	int    m_nPort;
	int   m_bRunning;
	int   m_bConnected;
	byte   m_RecvBuf[4000];
	pthread_t m_hThread;
	IOnRecv*  m_pOnRecv;
};
