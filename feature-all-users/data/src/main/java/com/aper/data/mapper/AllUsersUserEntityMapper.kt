package com.aper.data.mapper

import com.aper.app_database.entity.UserEntity
import com.aper.domain.model.AllUsersUser

fun UserEntity.toDomain(): AllUsersUser =
    AllUsersUser(
        id = id,
        login = login,
        name = name,
        avatarUrl = avatarUrl,
        bio = bio,
        followers = followers,
        following = following
    )