package com.aper.app_database.di

import com.aper.app_database.session.cache.SessionCacheManager
import com.aper.core_domain.session.cache.SessionCache
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SessionCacheModule {

    @Binds
    @Singleton
    fun bindRepositorySessionCache(
        impl: SessionCacheManager
    ): SessionCache
}
