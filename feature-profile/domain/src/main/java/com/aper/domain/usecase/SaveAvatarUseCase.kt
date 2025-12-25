package com.aper.domain.usecase

import com.aper.core.session.AppSessionData
import com.aper.core.session.SessionDataKey
import javax.inject.Inject

class SaveAvatarUseCase @Inject constructor(
    private val sessionData: AppSessionData
) {
    suspend operator fun invoke(uri: String) {
        sessionData.set(SessionDataKey.AvatarKey,uri.toString())
    }
}
