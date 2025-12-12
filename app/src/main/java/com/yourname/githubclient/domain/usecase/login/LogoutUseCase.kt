package com.yourname.githubclient.domain.usecase.login

import com.yourname.githubclient.data.local.DataStoreManager

class LogoutUseCase(private val dataStore: DataStoreManager) {
    suspend operator fun invoke() {
        dataStore.clearLoginState()
    }
}

