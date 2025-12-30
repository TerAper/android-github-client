package com.aper.data.di

import com.aper.data.repository.AllUsersRepositoryImpl
import com.aper.domain.repository.AllUsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AllUsersRepositoryModule {

    @Binds
    fun bindUsersRepository(
        impl: AllUsersRepositoryImpl
    ): AllUsersRepository
}
