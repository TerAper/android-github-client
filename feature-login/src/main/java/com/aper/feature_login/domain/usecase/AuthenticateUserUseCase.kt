package com.aper.feature_login.domain.usecase

import com.aper.feature_login.domain.repository.AuthRepository
import javax.inject.Inject

class AuthenticateUserUseCase @Inject constructor(private val repository: AuthRepository){
    suspend operator fun invoke(input: String, token: String) =
        repository.authenticate(input, token)
}


