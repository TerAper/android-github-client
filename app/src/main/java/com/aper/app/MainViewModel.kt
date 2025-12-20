package com.aper.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.app.ui.AppUiState
import com.aper.core.model.AppTheme
import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    session: AppSessionData
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    val themeFlow: StateFlow<AppTheme> =
        session.observe(SessionDataKey.ThemeKey)
            .map { it ?: AppTheme.SYSTEM }
            .distinctUntilChanged()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                AppTheme.SYSTEM
            )

    val isLoggedInFlow: StateFlow<Boolean> =
        session.observe(SessionDataKey.IsLoggedInKey)
            .map { it ?: false }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                false
            )

    fun showToolbar() {
        _uiState.update { it.copy(isToolbarVisible = true) }
    }

    fun hideToolbar() {
        _uiState.value = AppUiState()
    }

    fun setToolbarTitle(title: String?) {
        _uiState.update { it.copy(toolbarTitle = title) }
    }

    fun setBackEnabled(enabled: Boolean) {
        _uiState.update { it.copy(isBackEnabled = enabled) }
    }

    fun setSettingsVisible(visible: Boolean) {
        _uiState.update { it.copy(isSettingsVisible = visible) }
    }

}
