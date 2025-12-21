package com.aper.feature_login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.core.model.AppTheme
import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import com.aper.feature_login.domain.usecase.AuthenticateUserUseCase
import com.aper.feature_login.domain.usecase.SetLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: AuthenticateUserUseCase,
    private val setLoggedInUseCase: SetLoggedInUseCase,
    sessionData: AppSessionData

) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    val selectedTheme: StateFlow<AppTheme> =
        sessionData.observe(SessionDataKey.ThemeKey)
            .map { it ?: AppTheme.SYSTEM }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                AppTheme.SYSTEM
            )

    fun login(username: String, token: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = loginUseCase(username, token)

            result
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                    setLoggedInUseCase(true)
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message ?: "Login failed"
                        )
                    }
                }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
