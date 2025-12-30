package com.aper.domain.error

sealed class AuthException(cause: Throwable? = null) : Exception(cause) {

    class UsernameMismatch(cause: Throwable? = null) : AuthException(cause)

    class EmailMismatch(cause: Throwable? = null) : AuthException(cause)

    class Unknown(cause: Throwable? = null) : AuthException(cause)
}
