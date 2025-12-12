package com.yourname.githubclient.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourname.githubclient.domain.usecase.login.LoginUseCase
import com.yourname.githubclient.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _loginSuccess = MutableSharedFlow<Unit>()
    val loginSuccess = _loginSuccess.asSharedFlow()

    fun login(input: String, token: String) {
        if (input.isBlank()) { setError("Username or Email cannot be empty"); return }
        if (token.isBlank()) { setError("Token cannot be empty"); return }

        viewModelScope.launch {
            setLoading(true)
            val result = loginUseCase(input, token)
            setLoading(false)

            result.fold(
                onSuccess = { _loginSuccess.emit(Unit) },
                onFailure = { setError(it.message ?: "Login failed") }
            )
        }
    }
}

class Factory(
    private val loginUseCase: LoginUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
