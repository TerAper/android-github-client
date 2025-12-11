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

    override suspend fun login(token: String): Result<User> {
        return try {
            interceptor.setToken(token)

            val user = api.authenticate().toDomain()

            dataStore.saveToken(token)

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        interceptor.clearToken()
        dataStore.clearToken()
    }

    override suspend fun saveToken(token: String) {
        dataStore.saveToken(token)
        interceptor.setToken(token)
    }
}
