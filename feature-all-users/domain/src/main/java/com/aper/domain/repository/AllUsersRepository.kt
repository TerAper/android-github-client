package com.aper.domain.repository

import com.aper.domain.model.AllUsersUser


interface AllUsersRepository {
    suspend fun getUsers(page: Int, pageSize: Int): List<AllUsersUser>
    suspend fun clearCachedUsers()
    suspend fun clearCachedUsersRepos()
}
