package com.aper.data.di

import com.aper.data.api.ProfileReposApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileReposApiModule {

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): ProfileReposApi =
        retrofit.create(ProfileReposApi::class.java)
}
