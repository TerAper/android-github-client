package com.yourname.githubclient.domain.usecase

import com.yourname.githubclient.data.local.DataStoreManager

class LogoutUseCase(private val dataStore: DataStoreManager) {
    suspend operator fun invoke() = dataStore.clearAllExceptTheme()
}

