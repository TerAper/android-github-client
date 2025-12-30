package com.aper.core_android.di

import com.aper.core_android.resources.ResourceProvider
import com.aper.core_android.resources.ResourceProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ResourceModule {

    @Binds
    abstract fun bindResourceProvider(
        impl: ResourceProviderImpl
    ): ResourceProvider
}
