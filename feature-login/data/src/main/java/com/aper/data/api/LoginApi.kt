package com.aper.data.api

import com.aper.data.model.LogInUserDto
import com.aper.data.model.LogInUserEmailDto
import retrofit2.http.GET

interface LoginApi {
    @GET("user")
    suspend fun authenticate(): LogInUserDto

    @GET("user/emails")
    suspend fun getUserEmails(): List<LogInUserEmailDto>
}