package com.yourname.githubclient.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.yourname.githubclient.domain.model.User

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



fun UserEntity.toDomain() = User(
    id =  id,
    login = login,
    name = name?:"",
    avatarUrl = avatarUrl?:"",
    bio = bio?:"",
    followers = followers?:0,
    following = following?:0
)



