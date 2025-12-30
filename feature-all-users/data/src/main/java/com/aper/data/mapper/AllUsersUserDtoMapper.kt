package com.aper.data.mapper

import com.aper.app_database.entity.UserEntity
import com.aper.data.model.AllUsersUserDto
import com.aper.domain.model.AllUsersUser

fun AllUsersUserDto.toDomain(): AllUsersUser =
    AllUsersUser(
        id = id,
        login = login,
        name = name.orEmpty(),
        avatarUrl = avatarUrl.orEmpty(),
        bio = bio.orEmpty(),
        followers = followers ?: 0,
        following = following ?: 0
    )
fun AllUsersUserDto.toEntity(): UserEntity =
    UserEntity(
        id = id,
        login = login,
        name = name.orEmpty(),
        avatarUrl = avatarUrl.orEmpty(),
        bio = bio.orEmpty(),
        followers = followers ?: 0,
        following = following ?: 0
    )