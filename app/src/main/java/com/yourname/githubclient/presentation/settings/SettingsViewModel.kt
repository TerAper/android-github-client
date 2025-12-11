package com.yourname.githubclient.presentation.settings

import androidx.lifecycle.viewModelScope
import com.yourname.githubclient.presentation.base.BaseViewModel
import com.yourname.githubclient.domain.usecase.GetThemeUseCase
import com.yourname.githubclient.domain.usecase.UpdateThemeUseCase
import com.yourname.githubclient.domain.usecase.LogoutUseCase
import com.yourname.githubclient.util.ThemeManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getThemeUseCase: GetThemeUseCase,
    private val updateThemeUseCase: UpdateThemeUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    val isDark = getThemeUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun changeTheme(newValue: Boolean) {
        viewModelScope.launch {
            updateThemeUseCase(newValue)
            ThemeManager.apply(newValue)
        }
    }

    fun logout() {
        viewModelScope.launch { logoutUseCase() }
    }
}
