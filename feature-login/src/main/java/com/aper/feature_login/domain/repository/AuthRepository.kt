package com.aper.feature_login.domain.repository

import com.aper.core.model.User

interface AuthRepository {
    suspend fun authenticate(login: String, token: String): Result<User>
    suspend fun setLoggedIn(isLoggedIn: Boolean)
}

