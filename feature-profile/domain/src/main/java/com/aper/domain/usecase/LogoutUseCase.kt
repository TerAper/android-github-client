package com.aper.domain.usecase

import com.aper.core.session.AppSessionData
import com.aper.core.session.cache.SessionCacheManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val sessionData: AppSessionData,
    private val sessionCacheManager: SessionCacheManager
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        sessionData.clearSessionData()
        sessionCacheManager.clearAll()
    }
}
