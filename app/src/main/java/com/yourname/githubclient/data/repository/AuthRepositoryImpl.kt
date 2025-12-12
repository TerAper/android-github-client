package com.yourname.githubclient.data.repository

import com.yourname.githubclient.data.local.DataStoreManager
import com.yourname.githubclient.data.remote.api.GithubApi
import com.yourname.githubclient.data.remote.api.NetworkInterceptor
import com.yourname.githubclient.data.remote.model.toDomain
import com.yourname.githubclient.domain.model.User
import com.yourname.githubclient.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: GithubApi,
    private val dataStore: DataStoreManager,
    private val interceptor: NetworkInterceptor
) : AuthRepository {

    override suspend fun login(username: String, token: String): Result<User> {
        return try {
            saveToken(token)
            val isEmail = username.contains("@")

            if (isEmail) {
                val emails = api.getUserEmails()
                val emailMatch = emails.any { it.email.equals(username, ignoreCase = true) }
                if (!emailMatch) return Result.failure(Exception("Email does not match token"))
            } else {
                val user = api.authenticate().toDomain()
                if (user.username != username) return Result.failure(Exception("Username does not match token"))
            }

            dataStore.saveLoginState(token,username)

            val user = api.authenticate().toDomain()
            Result.success(user)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        clearToken()
        dataStore.clearLoginState()
    }


    override suspend fun saveToken(token: String) {
        interceptor.setToken(token)
    }

    override suspend fun clearToken() {
        interceptor.clearToken()
    }
}
