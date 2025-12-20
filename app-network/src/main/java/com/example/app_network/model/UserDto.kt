package com.example.app_network.model

import com.squareup.moshi.Json

data class UserDto(
    val id: Int,
    val login: String,
    val name: String?,

    @Json(name = "avatar_url")
    val avatarUrl: String?,
    
    val bio: String?,
    val followers: Int?,
    val following: Int?
)
