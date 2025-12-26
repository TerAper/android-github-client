package com.aper.domain.usecase

import com.aper.core.session.AppSessionData
import javax.inject.Inject

class GetAvatarUseCase @Inject constructor(
    private val sessionData: AppSessionData
) {
    operator fun invoke() = sessionData.observeAvatar()
}
