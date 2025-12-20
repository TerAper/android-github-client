package com.example.app_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app_database.dao.RepositoryDao
import com.example.app_database.dao.UserDao
import com.example.app_database.entity.RepositoryEntity
import com.example.app_database.entity.UserEntity

@Database(
    entities = [RepositoryEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
    abstract fun userDao(): UserDao
}
