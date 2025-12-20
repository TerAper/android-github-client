package com.example.app_database.mapper

import com.example.app_database.entity.UserEntity
import com.aper.core.model.User

fun UserEntity.toDomain(): User =
    User(
        id = id,
        login = login,
        name = name,
        avatarUrl = avatarUrl,
        bio = bio,
        followers = followers,
        following = following
    )

fun User.toEntity(): UserEntity = UserEntity(
    id = id,
    login = login,
    name = name,
    avatarUrl = avatarUrl,
    bio = bio,
    followers = followers,
    following = following
)