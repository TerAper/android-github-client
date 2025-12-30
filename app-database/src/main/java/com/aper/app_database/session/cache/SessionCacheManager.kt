package com.aper.app_database.session.cache

import com.aper.app_database.dao.RepositoryDao
import com.aper.app_database.dao.UserDao
import com.aper.core_domain.session.cache.SessionCache
import javax.inject.Inject

class SessionCacheManager @Inject constructor(
    private val repositoryDao: RepositoryDao,
    private val userDao: UserDao
) : SessionCache {

    override suspend fun clear() {
        repositoryDao.clearAllRepos()
        userDao.clearAllUsers()
    }
}
