package com.aper.feature_all_users.data.repository

import com.aper.core.model.User
import com.aper.core.network.NetworkChecker
import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import com.aper.feature_all_users.domain.repository.UsersRepository
import com.example.app_database.dao.RepositoryDao
import com.example.app_database.dao.UserDao
import com.example.app_database.mapper.toDomain
import com.example.app_database.mapper.toEntity
import com.example.app_network.api.GithubApi
import com.example.app_network.mapper.toDomain
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject


class UsersRepositoryImpl @Inject constructor(
    private val api: GithubApi,
    private val uDao: UserDao,
    private val rDao: RepositoryDao,
    private val networkChecker: NetworkChecker,
    private val sessionData: AppSessionData
) : UsersRepository {

    override suspend fun getUsers(page: Int, pageSize: Int): List<User> {
        return if (networkChecker.isOnline()) {
            if (page == 0) {
                clearCachedUsers()
            }
            val users = fetchFromApi(pageSize)
            cacheFetchedUsers(users)
            users
        } else {
            fetchFromDb(page, pageSize)
        }
    }

    private suspend fun fetchFromApi(pageSize: Int): List<User> {
        val since = uDao.getLastUserId() ?: 0

        val apiUsers = api.getUsers(since = since, perPage = pageSize)

        return apiUsers.map { it.toDomain() }
    }

    private suspend fun fetchFromDb(page: Int, pageSize: Int): List<User> {

        val offset = page * pageSize
        return uDao.getUsers(pageSize, offset).map { it.toDomain() }
    }

    override suspend fun cacheFetchedUsers(users: List<User>) {
        uDao.insertUsers(users.map { it.toEntity() })
    }

    override suspend fun clearCachedUsers() {
        uDao.clearAll()
        clearCachedUsersRepos()

    }

    override suspend fun clearCachedUsersRepos() {
        val profileUserName = sessionData.observe(SessionDataKey.LoginKey).firstOrNull() ?: return
        rDao.clearAllReposExcept(profileUserName)
    }

}
