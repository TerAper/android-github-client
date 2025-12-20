package com.aper.feature_user_details.domain.usecase

import com.aper.feature_user_details.domain.repository.DetailsRepository
import javax.inject.Inject

class GetUserReposUseCaseForDetails @Inject constructor(
    private val repo: DetailsRepository
) {
    suspend operator fun invoke(username: String) =
        repo.getUserRepos(username)
}
