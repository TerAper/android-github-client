package com.yourname.githubclient.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourname.githubclient.domain.model.User
import com.yourname.githubclient.domain.usecase.LoginUseCase
import com.yourname.githubclient.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _loginSuccess = MutableSharedFlow<User>()
    val loginSuccess = _loginSuccess.asSharedFlow()

    fun login(token: String) {
        if (token.isBlank()) {
            setError("Token cannot be empty")
            return
        }

        viewModelScope.launch {
            setLoading(true)

            val result = loginUseCase(token)

            setLoading(false)

            result.fold(
                onSuccess = { user ->
                    _loginSuccess.emit(user)
                },
                onFailure = { setError(it.message ?: "Login failed") }
            )
        }
    }
}


class LoginViewModelFactory(
    private val loginUseCase: LoginUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(loginUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
