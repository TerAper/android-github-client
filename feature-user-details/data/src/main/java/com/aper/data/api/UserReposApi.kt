package com.aper.data.api

import com.aper.data.model.UserReposDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UserReposApi {
    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ): List<UserReposDto>
}