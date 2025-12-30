package com.aper.domain.model

data class UserRepos(
    val id: Int,
    val owner: String,
    val name: String,
    val description: String?,
    val language: String?,
)