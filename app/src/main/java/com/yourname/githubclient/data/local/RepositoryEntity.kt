package com.yourname.githubclient.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yourname.githubclient.domain.model.Repository

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey val id: Int,
    val name: String
)
fun RepositoryEntity.toDomain() = Repository(
    id = id,
    name = name
)