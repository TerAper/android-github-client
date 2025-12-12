package com.yourname.githubclient.domain.usecase.profile

import com.yourname.githubclient.data.local.DataStoreManager

class ClearAvatarUseCase(private val prefs: DataStoreManager) {
    suspend operator fun invoke() {
        prefs.clearAvatar()
    }
}