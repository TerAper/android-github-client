package com.aper.feature_all_users.domain.repository

import com.aper.core.model.User


interface UsersRepository {
    suspend fun getUsers(page: Int, pageSize: Int): List<User>
    suspend fun clearCachedUsers()
    suspend fun clearCachedUsersRepos()
    suspend fun cacheFetchedUsers(users: List<User>)

}
