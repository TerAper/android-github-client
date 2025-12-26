package com.aper.app_database.di

import com.aper.app_database.settings.AppSettingsDataManager
import com.aper.core.settings.AppSettingsData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SettingsModule {

    @Binds
    @Singleton
    fun bindAppSessionData(
        impl: AppSettingsDataManager
    ): AppSettingsData
}