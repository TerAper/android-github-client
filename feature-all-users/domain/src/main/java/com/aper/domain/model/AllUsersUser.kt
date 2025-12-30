package com.aper.domain.model

data class AllUsersUser(
    val id: Int,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val bio: String,
    val followers: Int,
    val following: Int
)
