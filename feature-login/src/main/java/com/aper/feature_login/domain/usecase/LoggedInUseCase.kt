package com.aper.feature_login.domain.usecase

import com.aper.feature_login.domain.repository.AuthRepository
import javax.inject.Inject

class SetLoggedInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(isLoggedIn: Boolean) {
        repository.setLoggedIn(isLoggedIn)
    }
}
