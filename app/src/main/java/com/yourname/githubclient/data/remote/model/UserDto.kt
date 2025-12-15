package com.yourname.githubclient.data.remote.model

import com.yourname.githubclient.domain.model.UserDetails

data class UserDetailsDto(
    val login: String,
    val id: Int,
    val avatar_url: String?,
    val name: String?,
    val bio: String?,
    val followers: Int,
    val following: Int
)
fun UserDetailsDto.toDomain() = UserDetails(
    username = login,
    name = name,
    avatarUrl = avatar_url,
    bio = bio,
    followers = followers,
    following = following
)
