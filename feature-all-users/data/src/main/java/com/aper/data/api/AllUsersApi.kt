package com.aper.data.api

import com.aper.data.model.AllUsersUserDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AllUsersApi {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int = 20
    ): List<AllUsersUserDto>
}