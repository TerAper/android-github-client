package com.yourname.githubclient.data.remote.api

import com.yourname.githubclient.data.remote.model.UserDto
import retrofit2.http.GET

interface GithubApi {
    @GET("user")
    suspend fun authenticate(): UserDto
}
