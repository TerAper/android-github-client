package com.aper.core.session

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

    fun <T> observe(key: SessionDataKey<T>): Flow<T?>
    suspend fun <T> set(key: SessionDataKey<T>, value: T)

    suspend fun clearSessionData()
}
