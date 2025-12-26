package com.aper.core_domain.session

import kotlinx.coroutines.flow.Flow

interface AppSessionData {
    fun observeToken(): Flow<String?>
    fun observeLogin(): Flow<String?>
    fun observeAvatar(): Flow<String?>
    fun observeIsLoggedIn(): Flow<Boolean>

    suspend fun setToken(value: String)
    suspend fun setLogin(value: String)
    suspend fun setAvatar(value: String?)
    suspend fun setIsLoggedIn(value: Boolean)

    suspend fun clearSessionData()
}
