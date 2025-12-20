package com.aper.feature_profile.domain.usecase

import android.util.Log
import com.aper.core.sessionData.AppSessionData
import com.aper.feature_profile_repos.domain.repository.ProfileRepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val sessionData: AppSessionData,
    private val profileRepoRepository: ProfileRepoRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        profileRepoRepository.clearAllRepositories()
        sessionData.clearSessionData()
    }
}
