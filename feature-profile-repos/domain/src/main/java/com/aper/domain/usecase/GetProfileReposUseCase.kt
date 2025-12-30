package com.aper.domain.usecase

import com.aper.domain.model.ProfileRepos
import com.aper.domain.repasitory.ProfileRepoRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetProfileReposUseCase @Inject constructor(
    private val repository: ProfileRepoRepository
) {
    operator fun invoke(
        page: Int,
        perPage: Int,
        login: String
    ): Observable<List<ProfileRepos>> =
        repository.getProfileRepositories(page, perPage, login)
}
