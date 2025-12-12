package com.yourname.githubclient.data.remote.model

import com.yourname.githubclient.data.local.RepositoryEntity
import com.yourname.githubclient.domain.model.Repository

data class RepositoryDto(
    val id: Int,
    val name: String
) {
    fun toEntity() = RepositoryEntity(id, name)
    fun toDomain() = Repository(id = id, name = name)
}

