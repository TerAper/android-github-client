package com.yourname.githubclient.data.remote.api

import com.yourname.githubclient.data.remote.model.UserDto
import retrofit2.http.GET

data class UserEmailDto(
    val email: String,
    val primary: Boolean,
    val verified: Boolean,
    val visibility: String?
)

interface GithubApi {
    @GET("user")
    suspend fun authenticate(): UserDto

    @GET("user/emails")
    suspend fun getUserEmails(): List<UserEmailDto>
}
