package com.yourname.githubclient.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.yourname.githubclient.domain.model.Repository

@Entity(
    tableName = "repositories",
    indices = [Index("ownerLogin")]
)
data class RepositoryEntity(
    @PrimaryKey val id: Int,
    val ownerLogin: String,
    val name: String,
    val description: String?,
    val language: String?
)

fun RepositoryEntity.toDomain() = Repository(
    id = id,
    name = name,
    description = description,
    language = language
)