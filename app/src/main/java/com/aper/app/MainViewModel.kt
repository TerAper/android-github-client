package com.aper.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.app.ui.AppUiState
import com.aper.core.model.AppTheme
import com.aper.core.session.AppSessionData
import com.aper.core.session.SessionDataKey
import com.aper.core.settings.AppSettingsData
import com.aper.core.settings.SettingsDataKey
import com.aper.core.util.asState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    sessionData: AppSessionData,
    appSettingsData: AppSettingsData
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    val themeFlow: StateFlow<AppTheme> =
        appSettingsData.observe(SettingsDataKey.ThemeKey)
            .asState(viewModelScope, AppTheme.SYSTEM)


    val isLoggedInFlow: StateFlow<Boolean> =
        sessionData.observe(SessionDataKey.IsLoggedInKey)
            .map { it == true }
            .asState(viewModelScope, false)

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
