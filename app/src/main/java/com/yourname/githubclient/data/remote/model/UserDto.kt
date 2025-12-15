package com.yourname.githubclient.data.remote.model

import com.yourname.githubclient.data.local.model.UserEntity
import com.yourname.githubclient.domain.model.User

data class UserDto(
    val id: Int,
    val login: String,
    val name: String?,
    val avatar_url: String?,
    val bio: String?,
    val followers: Int?,
    val following: Int?
)

fun UserDto.toDomain() = User(
    id = id,
    login = login,
    name = name?:"",
    avatarUrl = avatar_url?:"",
    bio = bio?:"",
    followers = followers?:0,
    following = following?:0
)

fun UserDto.toEntity() = UserEntity(
    id = id,
    login = login,
    name = name ?: "",
    avatarUrl = avatar_url ?: "",
    bio = bio ?: "",
    followers = followers?:0,
    following = following?:0
)

