package com.aper.feature_login.data.repository

import com.aper.core.model.User
import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import com.aper.feature_login.domain.repository.AuthRepository
import com.example.app_network.api.GithubApi
import com.example.app_network.mapper.toDomain
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: GithubApi,
    private val sessionData: AppSessionData
) : AuthRepository {

    override suspend fun authenticate(login: String, token: String): Result<User> {
        sessionData.set(SessionDataKey.TokenKey, token)

        return try {

            val authenticatedUser = api.authenticate().toDomain()

            if (login.contains("@")) {

                val emails = api.getUserEmails()
                if (emails.none { it.email.equals(login, true) }) {
                    sessionData.clearSessionData()
                    return Result.failure(Exception("Email does not match token"))
                }
            } else if (authenticatedUser.login != login) {

                sessionData.clearSessionData()
                return Result.failure(Exception("Username does not match token"))
            }

            sessionData.set(SessionDataKey.LoginKey, authenticatedUser.login)
            Result.success(authenticatedUser)

        } catch (e: retrofit2.HttpException) {

            if (e.code() == 401) {
                sessionData.clearSessionData()
                Result.failure(Exception("Invalid token"))
            } else {

                sessionData.clearSessionData()
                Result.failure(e)
            }
        } catch (e: Exception) {
            sessionData.clearSessionData()
            Result.failure(e)
        }
    }

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        sessionData.set(SessionDataKey.IsLoggedInKey, isLoggedIn)
    }

}
