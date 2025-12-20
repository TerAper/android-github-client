package com.aper.feature_profile.domain.usecase

import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(
    private val sessionData: AppSessionData
) {
    operator fun invoke() = sessionData.observe(SessionDataKey.LoginKey)
}
