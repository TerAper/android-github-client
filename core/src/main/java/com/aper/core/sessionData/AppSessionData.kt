package com.aper.core.sessionData

import kotlinx.coroutines.flow.Flow

interface AppSessionData {
    fun <T> observe(key: SessionDataKey<T>): Flow<T?>
    suspend fun <T> set(key: SessionDataKey<T>, value: T)
    suspend fun clearSessionData()
}

