package com.aper.data.mapper

import com.aper.app_database.entity.RepositoryEntity
import com.aper.domain.model.UserRepos

fun RepositoryEntity.toDomain(): UserRepos =
    UserRepos(
        id = id,
        owner = owner,
        name = name,
        description = description,
        language = language
    )