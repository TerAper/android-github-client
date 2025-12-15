package com.yourname.githubclient.data.repository

import com.yourname.githubclient.data.local.DataStoreManager
import com.yourname.githubclient.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.combine

class ProfileRepositoryImpl(
    private val dataStore: DataStoreManager
) : ProfileRepository {

    override fun getProfile() =
        combine(dataStore.login, dataStore.avatarUriFlow) { login, uri ->
            login to uri
        }

    override suspend fun saveAvatar(uri: String) {
        dataStore.saveAvatar(uri)
    }

    override suspend fun clearAvatar() {
        dataStore.clearAvatar()
    }
}
