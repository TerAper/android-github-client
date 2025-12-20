package com.aper.core.model


data class User(
    val id: Int,
    val login: String,
    val name: String = "",
    val avatarUrl: String = "",
    val bio: String = "",
    val followers: Int = 0,
    val following: Int = 0
)
