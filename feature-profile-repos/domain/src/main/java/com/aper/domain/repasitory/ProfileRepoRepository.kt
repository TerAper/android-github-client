package com.aper.domain.repasitory

import com.aper.domain.model.ProfileRepos
import io.reactivex.rxjava3.core.Observable

interface ProfileRepoRepository {

    fun getProfileRepositories(
        page: Int,
        perPage: Int,
        login: String
    ): Observable<List<ProfileRepos>>

    fun clearAllRepositories()
}
