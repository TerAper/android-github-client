package com.aper.data.mapper

import com.aper.data.model.LogInUserDto
import com.aper.domain.model.LogInUser

fun LogInUserDto.toDomain(): LogInUser =
    LogInUser(
        id = id,
        login = login,
        name = name.orEmpty(),
        avatarUrl = avatarUrl.orEmpty(),
        bio = bio.orEmpty(),
        followers = followers ?: 0,
        following = following ?: 0
    )


