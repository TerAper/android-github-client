package com.aper.domain.usecase

import com.aper.core_domain.session.AppSessionData
import javax.inject.Inject

class SaveAvatarUseCase @Inject constructor(
    private val sessionData: AppSessionData
) {
    suspend operator fun invoke(uri: String) {
        sessionData.setAvatar(uri)
    }
}
