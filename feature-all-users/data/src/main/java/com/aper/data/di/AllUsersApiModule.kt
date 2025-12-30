package com.aper.data.di

import com.aper.data.api.AllUsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AllUsersApiModule {

    @Provides
    fun provideAllUsersApi(retrofit: Retrofit): AllUsersApi =
        retrofit.create(AllUsersApi::class.java)
}
