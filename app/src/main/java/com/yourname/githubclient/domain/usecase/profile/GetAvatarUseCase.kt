package com.yourname.githubclient.domain.usecase.profile

import android.net.Uri
import com.yourname.githubclient.data.local.DataStoreManager
import kotlinx.coroutines.flow.Flow

class GetAvatarUseCase(private val prefs: DataStoreManager) {
    operator fun invoke() = prefs.avatarUriFlow

}