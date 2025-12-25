package com.aper.domain.usecase

import com.aper.domain.repository.DetailsRepository
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val repo: DetailsRepository
) {
    suspend operator fun invoke(username: String) =
        repo.getUserRepos(username)
}
