package com.yourname.githubclient.domain.usecase.profile

import com.yourname.githubclient.data.local.DataStoreManager

class GetUsernameUseCase(private val prefs: DataStoreManager) {
    operator fun invoke() = prefs.userName
}
