package com.aper.data.mapper

import com.aper.app_database.entity.RepositoryEntity
import com.aper.data.modle.ProfileReposDto
import com.aper.domain.model.ProfileRepos

fun ProfileReposDto.toDomain(): ProfileRepos =
    ProfileRepos(
        id = id,
        owner = owner.login,
        name = name,
        description = description.orEmpty(),
        language = language.orEmpty()
    )

fun ProfileReposDto.toEntity(
    cachedOrder: Int = 0,
    profile: String
): RepositoryEntity =
    RepositoryEntity(
        id = id,
        owner = owner.login,
        name = name,
        description = description,
        language = language,
        cachedOrder = cachedOrder,
        profile = profile
    )