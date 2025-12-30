package com.aper.domain.error

sealed class AuthException : Exception() {

    data object UsernameMismatch : AuthException()

    data object EmailMismatch : AuthException()

    data class Unknown(override val cause: Throwable?) : AuthException()
}