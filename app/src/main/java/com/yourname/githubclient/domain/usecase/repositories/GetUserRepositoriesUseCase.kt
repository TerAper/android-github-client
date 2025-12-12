package com.yourname.githubclient.domain.usecase.repositories

import com.yourname.githubclient.domain.model.Repository
import com.yourname.githubclient.domain.repository.RepositoriesRepository
import io.reactivex.rxjava3.core.Observable

class GetUserRepositoriesUseCase(private val repository: RepositoriesRepository) {
    fun execute(page: Int, perPage: Int): Observable<List<Repository>> {
        return repository.getUserRepositories(page, perPage)
    }
}
