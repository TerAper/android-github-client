package com.yourname.githubclient.domain.usecase.profile
import android.net.Uri
import com.yourname.githubclient.data.local.DataStoreManager

class SaveAvatarUseCase(private val prefs: DataStoreManager) {
    suspend operator fun invoke(uri: Uri) {
        prefs.saveAvatar(uri.toString())
    }
}
