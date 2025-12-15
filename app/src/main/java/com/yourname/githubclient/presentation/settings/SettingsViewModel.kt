package com.yourname.githubclient.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourname.githubclient.domain.usecase.theme.GetThemeUseCase
import com.yourname.githubclient.domain.usecase.theme.UpdateThemeUseCase
import com.yourname.githubclient.presentation.base.BaseViewModel
import com.yourname.githubclient.util.ThemeManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingsViewModel(
    getThemeUseCase: GetThemeUseCase,
    private val updateThemeUseCase: UpdateThemeUseCase
) : BaseViewModel() {

    val isDark: Flow<Boolean> = getThemeUseCase()

    fun changeTheme(newValue: Boolean) {
        viewModelScope.launch {
            updateThemeUseCase(newValue)
            ThemeManager.apply(newValue)
        }
    }

    class Factory(
        private val getThemeUseCase: GetThemeUseCase,
        private val updateThemeUseCase: UpdateThemeUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(getThemeUseCase, updateThemeUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
