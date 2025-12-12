package com.yourname.githubclient.domain.repository

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<Pair<String?, String?>> // username, avatarUri
    suspend fun saveAvatar(uri: String)
    suspend fun clearAvatar()
}
