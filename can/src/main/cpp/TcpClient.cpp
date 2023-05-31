#include "TcpClient.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <netdb.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/ioctl.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "Utils.h"


TcpClient::TcpClient()
{
	m_pOnRecv = NULL;
	m_Socket = -1;
	m_nPort  = 0;
	m_hThread = 0;
	m_bRunning = 0;
	m_bConnected = 0;
}

TcpClient::~TcpClient()
{
	Close();
}

int TcpClient::Open(int nPort)
{
	m_nPort = nPort;
	
	Close();
	Connect();
	
	return BeginThread();
}

void TcpClient::Close()
{
	m_bRunning = 0;
	m_bConnected = 0;

	if (-1 != m_Socket) {
		close(m_Socket);
		m_Socket = -1;
	}
}

void TcpClient::SetOnRecv(IOnRecv* pOnRecv)
{
	m_pOnRecv = pOnRecv;
}

int TcpClient::Connect()
{
	if (-1 != m_Socket) {
		return 1;
	}

	m_Socket = socket(AF_INET, SOCK_STREAM, 0);
    
    if (-1 == m_Socket) {
        return 0;
    }

    int flags = fcntl(m_Socket, F_GETFL, 0);
    fcntl(m_Socket, F_SETFL, flags|O_NONBLOCK);

    struct sockaddr_in server_addr;
    bzero(&server_addr, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(m_nPort);
    server_addr.sin_addr.s_addr = inet_addr("127.0.0.1");

    int result = -1;
    int nRet = connect(m_Socket, (struct sockaddr *)(&server_addr), sizeof(struct sockaddr));
    if (0 == nRet) {
        result = 0;
    } else if (EINPROGRESS == errno) {
        fd_set wfds;
        FD_ZERO(&wfds);
        FD_SET(m_Socket, &wfds);

        struct timeval tv;
        tv.tv_sec = 2;
        tv.tv_usec = 0;

        nRet = select(m_Socket + 1, NULL, &wfds, NULL, &tv);
        switch (nRet)
        {
            case -1:
                result = -2;
                break;
            case 0:
                result = -3;
                break;
            default:
                if (FD_ISSET(m_Socket, &wfds))
                {

						result = 0;
                }
        }
    }
    
    if (result < 0) {
        close(m_Socket);
		m_Socket = -1;
        return 0;
    }

	m_bConnected = 1;
	return 1;
}

int TcpClient::BeginThread()
{
	if (0 != m_hThread) {
		m_hThread = 0;
	}

	m_bRunning = 1;

	if (pthread_create(&m_hThread, NULL, ThreadProc, this) != 0)
    {
        m_bRunning = 0;
    }

	return m_bRunning;
}

void* TcpClient::ThreadProc(void* lpParam)
{
	return ((TcpClient *)lpParam)->ThreadFunc();
}

int TcpClient::Send(const byte* pData, int nLength)
{
	if (-1 == m_Socket) {
		return 0;
	}

	timeval tv;
	tv.tv_sec = 3;
	tv.tv_usec = 0;

	fd_set fd_write;
	FD_ZERO(&fd_write);
	FD_SET(m_Socket, &fd_write);

	int iResult = select(m_Socket + 1, 0, &fd_write, 0, &tv);

	if (iResult <= 0) {
		return 0;
	}

	if (FD_ISSET(m_Socket, &fd_write)) {
		iResult = send(m_Socket, (const char*)pData, nLength, 0);

		if (-1 == iResult) {
			Close();
			return 0;
		}
	}

	return 1;
}

int TcpClient::IsRunning()
{
	return m_bRunning;
}

int TcpClient::IsConnected()
{
	return m_bConnected;
}

void* TcpClient::ThreadFunc()
{
	while (m_bRunning)
	{
		Work();

		m_bConnected = 0;
		if (!m_bRunning) {
			break;
		}

		usleep(50000);

		if (-1 != m_Socket) {
			close(m_Socket);
			m_Socket = -1;
		}
	}

	return NULL;
}

void TcpClient::Work()
{
	if (!Connect()) {
		return;
	}

	int iResult;
	timeval tv;
	fd_set fdRead;

	while (m_bRunning)
	{
		tv.tv_sec = 0;
		tv.tv_usec = 500000;
		
		FD_ZERO(&fdRead);
		FD_SET(m_Socket, &fdRead);

		iResult = select(m_Socket + 1, &fdRead, NULL, NULL, &tv);

		if (-1 == iResult)
		{
			break;
		}
		else
		{
			if (FD_ISSET(m_Socket, &fdRead))
			{
				int length = recv(m_Socket, (char*)m_RecvBuf, 3000, 0);

				if (NULL != m_pOnRecv) {
					m_pOnRecv->OnRecv(m_RecvBuf, length);
				}
			}
		}
	}
}
