package com.example.app_network.model

data class RepositoryDto(
    val id: Int,
    val owner: OwnerDto,
    val name: String,
    val description: String?,
    val language: String?,
)
data class OwnerDto(
    val login: String
)