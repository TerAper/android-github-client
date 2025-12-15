package com.yourname.githubclient.domain.repository

import com.yourname.githubclient.domain.model.User

interface AuthRepository {
    suspend fun login(login: String, token: String): Result<User>
    suspend fun logout()
    suspend fun saveToken(token: String)
    suspend fun clearToken()

}

