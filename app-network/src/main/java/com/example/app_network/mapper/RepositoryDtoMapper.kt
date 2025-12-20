package com.example.app_network.mapper

import com.example.app_network.model.RepositoryDto
import com.aper.core.model.Repository

fun RepositoryDto.toDomain(): Repository =
    Repository(
        id = id,
        owner = owner.login,
        name = name,
        description = description.orEmpty(),
        language = language.orEmpty()
    )
