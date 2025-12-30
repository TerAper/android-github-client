package com.aper.data.api

import com.aper.data.modle.ProfileReposDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileReposApi {
    @GET("user/repos")
    fun getUserRepositories(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Observable<List<ProfileReposDto>>
}