package com.yourname.githubclient.domain.usecase

import com.yourname.githubclient.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(token: String) =
        repository.login(token)
}
