package com.aper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.core_android.resources.ResourceProvider
import com.aper.domain.error.AuthException
import com.aper.domain.usecase.AuthenticateUserUseCase
import com.aper.domain.usecase.ClearSessionUseCase
import com.aper.domain.usecase.CompleteLoginUseCase
import com.aper.feature_login.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticateUser: AuthenticateUserUseCase,
    private val completeLogin: CompleteLoginUseCase,
    private val clearSession: ClearSessionUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = Channel<LoginUiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onUserNameChange(value: String) {
        _uiState.update { it.copy(userName = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun login() {
        val state = _uiState.value

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            authenticateUser(state.userName, state.password)
                .onSuccess { user ->
                    completeLogin(true, user.login)
                    _uiState.update { it.copy(isLoading = false) }
                }
                .onFailure { e ->
                    clearSession()
                    val authError = e as AuthException

                    val error = when (authError) {
                        is AuthException.UsernameMismatch ->
                            resourceProvider.string(
                                R.string.error_username_mismatch,
                                uiState.value.userName,
                            )

                        is AuthException.EmailMismatch ->
                            resourceProvider.string(
                                R.string.error_email_mismatch,
                                uiState.value.userName
                            )

                        is AuthException.Unknown ->
                            resourceProvider.string(R.string.error_auth_failed)

                    }

                    _uiState.update { it.copy(isLoading = false) }

                    _events.send(LoginUiEvent.ShowSnackbar(error))
                }
        }
    }

}
