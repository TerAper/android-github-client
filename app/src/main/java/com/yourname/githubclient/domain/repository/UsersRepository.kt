package com.yourname.githubclient.domain.repository

import com.yourname.githubclient.domain.model.User


interface UsersRepository {
    suspend fun getUsers(page: Int, pageSize: Int): List<User>
    suspend fun clearCachedUsers()
    suspend fun clearCachedUsersIfOnline()
    suspend fun cacheFetchedUsers(users: List<User>)

}
