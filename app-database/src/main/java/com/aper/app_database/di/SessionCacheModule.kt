package com.aper.app_database.di

import com.aper.app_database.session.cache.RepositorySessionCacheManager
import com.aper.core.session.cache.SessionCache
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionCacheModule {

    @Binds
    @IntoSet
    @Singleton
    abstract fun bindRepositorySessionCache(
        impl: RepositorySessionCacheManager
    ): SessionCache
}
