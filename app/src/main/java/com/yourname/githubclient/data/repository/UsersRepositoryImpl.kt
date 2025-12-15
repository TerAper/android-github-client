package com.yourname.githubclient.data.repository

import android.util.Log
import com.yourname.githubclient.data.local.DataStoreManager
import com.yourname.githubclient.data.local.RepositoryDao
import com.yourname.githubclient.data.local.UserDao
import com.yourname.githubclient.data.local.model.toDomain
import com.yourname.githubclient.data.remote.api.GithubApi
import com.yourname.githubclient.data.remote.model.toEntity
import com.yourname.githubclient.domain.model.User
import com.yourname.githubclient.domain.model.toEntity
import com.yourname.githubclient.domain.repository.UsersRepository
import com.yourname.githubclient.util.NetworkChecker

class UsersRepositoryImpl(
    private val api: GithubApi,
    private val uDao: UserDao,
    private val rDao: RepositoryDao,
    private val networkChecker: NetworkChecker,
    private val dataStore: DataStoreManager
) : UsersRepository {

    override suspend fun getUsers(page: Int, pageSize: Int): List<User> {

        return if (networkChecker.isOnline()) {
            val users = fetchFromApi(pageSize)
            cacheFetchedUsers(users)
            return users
        } else {
            fetchFromDb(page, pageSize)
        }
    }

    private suspend fun fetchFromApi(pageSize: Int): List<User> {
        val since = uDao.getLastUserId() ?: 0

        val apiUsers = api.getUsers(
            since = since,
            perPage = pageSize
        )

        val entities = apiUsers.map { it.toEntity() }
        return entities.map { it.toDomain() }
    }

    private suspend fun fetchFromDb(page: Int, pageSize: Int): List<User> {
        val offset = page * pageSize
        return uDao.getUsers(limit = pageSize, offset = offset)
            .map { it.toDomain() }
    }


    override suspend fun cacheFetchedUsers(users: List<User>) {
        uDao.insertUsers(users.map { it.toEntity() })
    }
    override suspend fun clearCachedUsers() {
        uDao.clearAll()
    }

    override suspend fun clearCachedUsersIfOnline() {
        if(networkChecker.isOnline()){
            uDao.clearAll()
            rDao.clearRepos(dataStore.userName?:"")
        }
    }



}
