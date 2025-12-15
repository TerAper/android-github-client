package com.yourname.githubclient.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.yourname.githubclient.domain.model.Repository

@Entity(
    tableName = "repositories",
    indices = [Index("owner")]
)
data class RepositoryEntity(
    @PrimaryKey val id: Int,
    val owner: String,
    val name: String,
    val description: String?,
    val language: String?,
    val cachedOrder: Int,
    val profile: String
)

fun RepositoryEntity.toDomain() = Repository(
    id = id,
    name = name,
    description = description ?: "",
    language = language ?: ""
)


