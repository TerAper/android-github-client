package com.aper.data.di

import com.aper.data.repository.DetailsRepositoryImpl
import com.aper.domain.repository.DetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DetailsRepositoryModule {

    @Binds
    fun bindDetailsRepository(
        impl: DetailsRepositoryImpl
    ): DetailsRepository
}