package com.aper.domain.model

data class ProfileRepos(
    val id: Int,
    val owner: String,
    val name: String,
    val description: String?,
    val language: String?,
)