package com.yourname.githubclient.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RepositoryEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
    abstract fun userDao(): UserDao
}
