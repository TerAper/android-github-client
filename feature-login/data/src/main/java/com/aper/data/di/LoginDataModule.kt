package com.aper.data.di

import com.aper.data.api.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginDataModule {

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi =
        retrofit.create(LoginApi::class.java)
}
