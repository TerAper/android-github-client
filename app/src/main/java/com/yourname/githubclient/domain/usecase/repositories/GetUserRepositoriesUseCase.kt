package com.yourname.githubclient.domain.usecase.repositories

import com.yourname.githubclient.domain.model.Repository
import com.yourname.githubclient.domain.repository.ProfileRepoRepository
import io.reactivex.rxjava3.core.Observable

class GetUserRepositoriesUseCase(private val repository: ProfileRepoRepository) {
    operator fun invoke (page: Int, perPage: Int,login: String): Observable<List<Repository>> {
        return repository.getProfileRepositories(page, perPage,login)
    }
}
