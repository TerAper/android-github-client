package com.aper.data.mapper

import com.aper.app_database.entity.RepositoryEntity
import com.aper.domain.model.ProfileRepos

fun RepositoryEntity.toDomain(): ProfileRepos =
    ProfileRepos(
        id = id,
        owner = owner,
        name = name,
        description = description,
        language = language
    )