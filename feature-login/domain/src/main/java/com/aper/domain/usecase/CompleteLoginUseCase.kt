package com.aper.domain.usecase

import com.aper.core.session.AppSessionData
import com.aper.core.session.SessionDataKey
import javax.inject.Inject

class CompleteLoginUseCase @Inject constructor(
    private val sessionData: AppSessionData
) {
    suspend operator fun invoke(token: String, login: String) {
        sessionData.set(SessionDataKey.TokenKey, token)
        sessionData.set(SessionDataKey.LoginKey, login)
        sessionData.set(SessionDataKey.IsLoggedInKey, true)
    }
}
