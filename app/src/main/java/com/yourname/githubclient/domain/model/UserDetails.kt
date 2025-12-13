package com.yourname.githubclient.domain.model

data class UserDetails(
    val username: String?,
    val name: String?,
    val avatarUrl: String?,
    val bio: String?,
    val followers: Int,
    val following: Int
)
