package com.yourname.githubclient.data.remote.model

import com.yourname.githubclient.domain.model.User

data class UserDto(
 val id: Long,
 val login: String,
 val avatar_url: String?
)


fun UserDto.toDomain(): User {
 return User(
  id = id,
  username = login,
  avatarUrl = avatar_url
 )
}

