package com.yourname.githubclient.domain.usecase.details

import com.yourname.githubclient.domain.repository.DetailsRepository

class GetUserReposUseCaseForDetails(private val repo: DetailsRepository) {
    suspend operator fun invoke(username: String) =
        repo.getUserRepos(username)
}
