package com.aper.data.mapper

import com.aper.app_database.entity.RepositoryEntity
import com.aper.data.model.UserReposDto
import com.aper.domain.model.UserRepos

fun UserReposDto.toDomain(): UserRepos =
    UserRepos(
        id = id,
        owner = owner.login,
        name = name,
        description = description.orEmpty(),
        language = language.orEmpty()
    )

fun UserReposDto.toEntity(
    cachedOrder: Int = 0,
    profile: String = ""
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