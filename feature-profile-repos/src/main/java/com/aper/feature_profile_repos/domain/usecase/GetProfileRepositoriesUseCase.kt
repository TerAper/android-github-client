package com.aper.feature_profile_repos.domain.usecase

import com.aper.core.model.Repository
import com.aper.feature_profile_repos.domain.repository.ProfileRepoRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetProfileRepositoriesUseCase @Inject constructor(
    private val repository: ProfileRepoRepository
) {
    operator fun invoke(
        page: Int,
        perPage: Int,
        login: String
    ): Observable<List<Repository>> =
        repository.getProfileRepositories(page, perPage, login)
}
