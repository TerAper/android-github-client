package com.yourname.githubclient.domain.repository

import com.yourname.githubclient.domain.model.User


interface UsersRepository {
    suspend fun getUsers(page: Int, pageSize: Int): List<User>
    suspend fun cacheUsers(users: List<User>)
    suspend fun getCachedUsers(limit: Int = 100, offset: Int = 0): List<User>
    suspend fun getLastCachedUserId(): Int?
}
