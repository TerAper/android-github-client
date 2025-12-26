package com.aper.app_database.di

import com.aper.core.session.AppSessionData
import com.aper.app_database.session.AppSessionDataManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SessionDataModule {

    @Binds
    @Singleton
    fun bindAppSessionData(
        impl: AppSessionDataManager
    ): AppSessionData
}
