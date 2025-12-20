package com.aper.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.core.model.AppTheme
import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sessionData: AppSessionData
) : ViewModel() {

    val selectedTheme: StateFlow<AppTheme> =
        sessionData.observe(SessionDataKey.ThemeKey)
            .map { it ?: AppTheme.SYSTEM }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                AppTheme.SYSTEM
            )

    fun onThemeSelected(theme: AppTheme) {
        viewModelScope.launch {
            sessionData.set(SessionDataKey.ThemeKey, theme)
        }
    }
}
