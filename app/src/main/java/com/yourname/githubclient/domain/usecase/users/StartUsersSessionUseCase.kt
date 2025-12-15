package com.yourname.githubclient.domain.usecase.users

import com.yourname.githubclient.domain.repository.UsersRepository

class StartUsersSessionUseCase(
    private val repository: UsersRepository
) {
    suspend operator fun invoke() {
        repository.clearCachedUsersIfOnline()
    }
}
