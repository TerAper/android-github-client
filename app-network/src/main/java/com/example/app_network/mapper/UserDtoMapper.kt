package com.example.app_network.mapper

import com.example.app_network.model.UserDto
import com.aper.core.model.User

fun UserDto.toDomain(): User =
    User(
        id = id,
        login = login,
        name = name.orEmpty(),
        avatarUrl = avatarUrl.orEmpty(),
        bio = bio.orEmpty(),
        followers = followers ?: 0,
        following = following ?: 0
    )


