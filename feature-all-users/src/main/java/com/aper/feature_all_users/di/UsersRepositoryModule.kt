package com.aper.feature_all_users.di

import com.aper.feature_all_users.data.repository.UsersRepositoryImpl
import com.aper.feature_all_users.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UsersRepositoryModule {

    @Binds
    abstract fun bindUsersRepository(
        impl: UsersRepositoryImpl
    ): UsersRepository
}
