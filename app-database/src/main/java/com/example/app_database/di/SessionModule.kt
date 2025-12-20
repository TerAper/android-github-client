package com.example.app_database.di

import com.aper.core.sessionData.AppSessionData
import com.example.app_database.session.AppSessionDataManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    @Singleton
    abstract fun bindAppSessionData(
        impl: AppSessionDataManager
    ): AppSessionData
}
