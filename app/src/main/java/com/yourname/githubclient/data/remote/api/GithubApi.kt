package com.yourname.githubclient.data.remote.api

import com.yourname.githubclient.data.remote.model.RepositoryDto
import com.yourname.githubclient.data.remote.model.UserDetailsDto
import com.yourname.githubclient.data.remote.model.UserDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("user/repos")
    fun getUserRepositories(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Observable<List<RepositoryDto>>

    @GET("users")
    suspend fun getAllUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = 20
    ): List<UserDto>

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): UserDetailsDto

    @GET("users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ): List<RepositoryDto>

}
