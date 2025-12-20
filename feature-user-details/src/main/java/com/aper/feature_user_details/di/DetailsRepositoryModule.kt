package com.aper.feature_user_details.di

import com.aper.feature_user_details.data.repository.DetailsRepositoryImpl
import com.aper.feature_user_details.domain.repository.DetailsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailsRepositoryModule {

    @Binds
    abstract fun bindDetailsRepository(
        impl: DetailsRepositoryImpl
    ): DetailsRepository
}