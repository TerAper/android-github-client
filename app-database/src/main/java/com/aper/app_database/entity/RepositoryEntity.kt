package com.aper.app_database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
