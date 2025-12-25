package com.aper.app_database.di

import android.content.Context
import androidx.room.Room
import com.aper.app_database.dao.RepositoryDao
import com.aper.app_database.dao.UserDao
import com.aper.app_database.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun provideRepositoryDao(db: AppDatabase): RepositoryDao = db.repositoryDao()
}
