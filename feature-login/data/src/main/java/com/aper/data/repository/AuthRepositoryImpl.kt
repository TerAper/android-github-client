package com.aper.data.repository

import com.aper.core.session.AppSessionData
import com.aper.core.session.SessionDataKey
import com.aper.data.api.LoginApi
import com.aper.data.mapper.toDomain
import com.aper.domain.model.LogInUser
import com.aper.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: LoginApi,
    private val sessionData: AppSessionData
) : AuthRepository {

    override suspend fun authenticate(
        login: String,
        token: String
    ): Result<LogInUser> {
        return try {
            sessionData.set(SessionDataKey.TokenKey,token)
            val user = api.authenticate().toDomain()

            if (login.contains("@")) {
                val emails = api.getUserEmails()

                val matchedEmail = emails
                    .filter { it.verified }
                    .sortedByDescending { it.primary }
                    .firstOrNull { it.email.equals(login, true) }

                if (matchedEmail == null) {
                    return Result.failure(
                        Exception("Verified email does not match token")
                    )
                }
            } else {
                if (!user.login.equals(login, ignoreCase = true)) {
                    return Result.failure(
                        Exception("Username does not match token")
                    )
                }
            }

            Result.success(user)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
