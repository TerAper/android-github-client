package com.aper.feature_profile_repos.domain.repository

import com.aper.core.model.Repository
import io.reactivex.rxjava3.core.Observable

interface ProfileRepoRepository {

    fun getProfileRepositories(
        page: Int,
        perPage: Int,
        login: String
    ): Observable<List<Repository>>

    fun clearAllRepositories()
}
