package com.yourname.githubclient.domain.usecase.login

import com.yourname.githubclient.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(input: String, token: String) =
        repository.login(input, token)
}


