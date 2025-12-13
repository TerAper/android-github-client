package com.yourname.githubclient.data.remote.model

import com.yourname.githubclient.data.local.RepositoryEntity
import com.yourname.githubclient.domain.model.Repository

data class RepositoryDto(
    val id: Int,
    val name: String,
    val description: String?,
    val language: String?,
    val owner: OwnerDto
)

data class OwnerDto(
    val login: String
)

fun RepositoryDto.toEntity() = RepositoryEntity(
    id = id,
    ownerLogin = owner.login,
    name = name,
    description = description,
    language = language
)


fun RepositoryDto.toDomain() = Repository(
    id = id,
    name = name,
    description = description,
    language = language
)





