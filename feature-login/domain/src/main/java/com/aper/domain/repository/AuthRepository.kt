package com.aper.domain.repository

import com.aper.domain.model.LogInUser

interface AuthRepository {
    suspend fun authenticate(login: String, token: String): Result<LogInUser>
}

