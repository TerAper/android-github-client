package com.aper.domain.usecase

import com.aper.core_domain.session.AppSessionData
import javax.inject.Inject

class CompleteLoginUseCase @Inject constructor(
    private val sessionData: AppSessionData
) {
    suspend operator fun invoke(loggedIn: Boolean, login: String) {
        sessionData.setLogin(login)
        sessionData.setIsLoggedIn(loggedIn)
    }
}
