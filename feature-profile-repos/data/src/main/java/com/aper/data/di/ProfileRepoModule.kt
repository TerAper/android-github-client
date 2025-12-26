package com.aper.data.di

import com.aper.data.repository.ProfileRepoRepositoryImpl
import com.aper.domain.repasitory.ProfileRepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProfileRepoModule {

    @Binds
    fun bindProfileRepoRepository(
        impl: ProfileRepoRepositoryImpl
    ): ProfileRepoRepository
}
