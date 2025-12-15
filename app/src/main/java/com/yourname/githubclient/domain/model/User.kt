package com.yourname.githubclient.domain.model

import com.yourname.githubclient.data.local.model.UserEntity


data class User(
    val id: Int,
    val login: String,
    val name: String = "",
    val avatarUrl: String = "",
    val bio: String = "",
    val followers: Int = 0,
    val following: Int = 0
)

fun User.toEntity() = UserEntity(
    id =  id,
    login = login,
    name = name?:"",
    avatarUrl = avatarUrl?:"",
    bio = bio?:"",
    followers = followers?:0,
    following = following?:0
)