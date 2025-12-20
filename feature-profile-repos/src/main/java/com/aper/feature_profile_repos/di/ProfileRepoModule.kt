package com.aper.feature_profile_repos.di

import com.aper.feature_profile_repos.data.repository.ProfileRepoRepositoryImpl
import com.aper.feature_profile_repos.domain.repository.ProfileRepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileRepoModule {

    @Binds
    abstract fun bindProfileRepoRepository(
        impl: ProfileRepoRepositoryImpl
    ): ProfileRepoRepository
}
