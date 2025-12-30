package com.aper.domain.usecase

import com.aper.core_domain.session.AppSessionData
import com.aper.core_domain.session.cache.SessionCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val sessionData: AppSessionData,
    private val sessionCacheData: SessionCache
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        sessionData.clearSessionData()
        sessionCacheData.clear()
    }
}
