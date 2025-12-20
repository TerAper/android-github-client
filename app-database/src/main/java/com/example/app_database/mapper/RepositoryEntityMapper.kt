package com.example.app_database.mapper

import com.example.app_database.entity.RepositoryEntity
import com.aper.core.model.Repository

fun RepositoryEntity.toDomain(): Repository =
    Repository(
        id = id,
        owner = owner,
        name = name,
        description = description.orEmpty(),
        language = language.orEmpty()
    )

fun Repository.toEntity(
    cachedOrder: Int = 0,
    profile: String = ""
) = RepositoryEntity(
    id = id,
    owner = owner,
    name = name,
    description = description,
    language = language,
    cachedOrder = cachedOrder,
    profile = profile
)