package com.aper.data.model

import com.squareup.moshi.Json

data class LogInUserDto(
    @Json(name = "id") val id: Int,
    @Json(name = "login") val login: String,
    @Json(name = "name") val name: String?,
    @Json(name = "avatar_url") val avatarUrl: String?,
    @Json(name = "bio") val bio: String?,
    @Json(name = "followers") val followers: Int?,
    @Json(name = "following") val following: Int?
)
