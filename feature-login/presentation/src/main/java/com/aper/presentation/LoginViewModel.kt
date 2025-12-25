package com.aper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.core.model.AppTheme
import com.aper.core.settings.AppSettingsData
import com.aper.core.util.asState
import com.aper.domain.usecase.AuthenticateUserUseCase
import com.aper.domain.usecase.ClearSessionUseCase
import com.aper.domain.usecase.CompleteLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticateUser: AuthenticateUserUseCase,
    private val completeLogin: CompleteLoginUseCase,
    private val clearSession: ClearSessionUseCase,
    appSettingsData: AppSettingsData
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    val selectedTheme: StateFlow<AppTheme> = appSettingsData.observeTheme()
        .asState(viewModelScope, AppTheme.SYSTEM)

    fun onUserNameChange(value: String) {
        _uiState.update { it.copy(userName = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun login() {
        val state = _uiState.value

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            authenticateUser(state.userName, state.password)
                .onSuccess { user ->
                    completeLogin(
                        token = state.password,
                        login = user.login
                    )
                    _uiState.update { it.copy(isLoading = false) }
                }
                .onFailure { e ->
                    clearSession()
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "Authentication failed"
                        )
                    }
                }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
