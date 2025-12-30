package com.aper.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogInUserDto(
    @SerialName("id") val id: Int,
    @SerialName("login") val login: String,
    @SerialName("name") val name: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("bio") val bio: String? = null,
    @SerialName("followers") val followers: Int? = null,
    @SerialName("following") val following: Int? = null
)
