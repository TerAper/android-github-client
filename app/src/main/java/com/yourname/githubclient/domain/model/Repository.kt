package com.yourname.githubclient.domain.model

data class Repository(
    val id: Int,
    val name: String,
    val description: String? ,
    val language: String? ,
    val ownerLogin: String = ""
)
