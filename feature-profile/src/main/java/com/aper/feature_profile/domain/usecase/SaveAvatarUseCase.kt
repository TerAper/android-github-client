package com.aper.feature_profile.domain.usecase

import android.net.Uri
import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import javax.inject.Inject

class SaveAvatarUseCase @Inject constructor(
    private val sessionData: AppSessionData
) {
    suspend operator fun invoke(uri: Uri) {
        sessionData.set(SessionDataKey.AvatarKey,uri.toString())
    }
}
