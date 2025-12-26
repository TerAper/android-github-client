package com.aper.data.repository

import com.aper.core_domain.session.AppSessionData
import com.aper.app_database.dao.RepositoryDao
import com.aper.app_database.dao.UserDao
import com.aper.core_android.network.NetworkChecker
import com.aper.data.api.AllUsersApi
import com.aper.data.mapper.toDomain
import com.aper.data.mapper.toEntity
import com.aper.domain.model.AllUsersUser
import com.aper.domain.repository.AllUsersRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject


class AllUsersRepositoryImpl @Inject constructor(
    private val api: AllUsersApi,
    private val userDao: UserDao,
    private val repoDao: RepositoryDao,
    private val networkChecker: NetworkChecker,
    private val sessionData: AppSessionData
) : AllUsersRepository {

    override suspend fun getUsers(page: Int, pageSize: Int): List<AllUsersUser> {
        return if (networkChecker.isOnline()) {

            val apiUsers = api.getUsers(page, perPage = pageSize)

            if (page == 0 && apiUsers.isNotEmpty()) {
                clearCachedUsers()
                clearCachedUsersRepos()
            }

            val entityUsers = apiUsers.map { it.toEntity() }
            userDao.insertUsers(entityUsers)

            entityUsers.map { it.toDomain() }
        } else {
            val offset = page * pageSize
            return userDao.getUsers(pageSize, offset).map { it.toDomain() }
        }
    }


    override suspend fun clearCachedUsers() {
        userDao.clearAllUsers()
    }

    override suspend fun clearCachedUsersRepos() {
        val profileUserName = sessionData.observeLogin().firstOrNull() ?: return
        repoDao.clearAllReposExcept(profileUserName)
    }


}
