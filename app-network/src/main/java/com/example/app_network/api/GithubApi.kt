package com.example.app_network.api

import com.example.app_network.model.RepositoryDto
import com.example.app_network.model.UserDto
import com.example.app_network.model.UserEmailDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("user")
    suspend fun authenticate(): UserDto

    @GET("user/emails")
    suspend fun getUserEmails(): List<UserEmailDto>

    @GET("user/repos")
    fun getUserRepositories(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Observable<List<RepositoryDto>>

    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = 20
    ): List<UserDto>

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): UserDto

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ): List<RepositoryDto>

}
