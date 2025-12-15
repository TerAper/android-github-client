package com.yourname.githubclient.data.remote.model

import com.yourname.githubclient.data.local.model.RepositoryEntity
import com.yourname.githubclient.domain.model.Repository

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

fun RepositoryDto.toEntity(
    cachedOrder: Int = 0,
    profile: String = ""
) = RepositoryEntity(
    id = id,
    owner = owner.login,
    name = name,
    description = description,
    language = language,
    cachedOrder = cachedOrder,
    profile = profile
)



fun RepositoryDto.toDomain() = Repository(
    id = id,
    name = name,
    description = description ?:"",
    language = language ?:""
)





