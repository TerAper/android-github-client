package com.yourname.githubclient.domain.usecase.login

import com.yourname.githubclient.data.local.DataStoreManager
import com.yourname.githubclient.domain.repository.ProfileRepoRepository
import com.yourname.githubclient.domain.repository.UsersRepository

class LogoutUseCase(private val dataStore: DataStoreManager,
                    private val profileRepoRepository: ProfileRepoRepository,
                    private val usersRepository: UsersRepository,
) {
    suspend operator fun invoke() {
        dataStore.clearLoginState()
        usersRepository.clearCachedUsers()
        profileRepoRepository.clearAllRepositories()
    }
}

