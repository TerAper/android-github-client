package com.yourname.githubclient.domain.repository

import com.yourname.githubclient.domain.model.Repository
import io.reactivex.rxjava3.core.Observable

interface ProfileRepoRepository {
    fun getProfileRepositories(page: Int, perPage: Int,login: String): Observable<List<Repository>>
    suspend fun clearAllRepositories()
}
