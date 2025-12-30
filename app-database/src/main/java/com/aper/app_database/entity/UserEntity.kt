package com.aper.app_database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["login"], unique = true)]
)
data class UserEntity(
    @PrimaryKey val id: Int,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val bio: String,
    val followers: Int,
    val following: Int
)






