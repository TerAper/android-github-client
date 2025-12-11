package com.yourname.githubclient.domain.repository

import com.yourname.githubclient.domain.model.User

interface AuthRepository {
    suspend fun login(token: String): Result<User>
    suspend fun saveToken(token: String)
    suspend fun logout()
}
