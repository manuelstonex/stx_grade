#include "Lock.h"

CLock::CLock()
{
	pthread_mutex_init(&m_Mutex, NULL);
}

CLock::~CLock()
{
}

bool CLock::Lock() {
	pthread_mutex_lock(&m_Mutex);
	return true;
}

bool CLock::Unlock()
{
	pthread_mutex_unlock(&m_Mutex);

		return true;

}
