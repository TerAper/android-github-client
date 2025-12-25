package com.aper.core.session.cache

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionCacheManager @Inject constructor(
    private val caches: Set<@JvmSuppressWildcards SessionCache>
) {
    suspend fun clearAll() {
        caches.forEach { it.clear() }
    }
}
