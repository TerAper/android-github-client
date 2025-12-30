package com.aper.domain.usecase

import com.aper.core_domain.session.AppSessionData
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor(
    private val sessionData: AppSessionData
) {
    suspend operator fun invoke() {
        sessionData.clearSessionData()
    }
}
