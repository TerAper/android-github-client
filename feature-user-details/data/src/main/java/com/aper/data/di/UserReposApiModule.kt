package com.aper.data.di

import com.aper.data.api.UserReposApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserReposApiModule {

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): UserReposApi =
        retrofit.create(UserReposApi::class.java)
}
