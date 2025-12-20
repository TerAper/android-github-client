package com.example.app_network.model

data class UserEmailDto(
    val email: String,
    val primary: Boolean,
    val verified: Boolean,
    val visibility: String?
)