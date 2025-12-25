package com.aper.domain.usecase

import com.aper.domain.repository.AuthRepository
import javax.inject.Inject

class AuthenticateUserUseCase @Inject constructor(private val repository: AuthRepository){
    suspend operator fun invoke(input: String, token: String) =
        repository.authenticate(input, token)
}


