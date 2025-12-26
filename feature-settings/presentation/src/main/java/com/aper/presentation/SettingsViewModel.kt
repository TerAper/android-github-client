package com.aper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.core_domain.model.AppTheme
import com.aper.core_domain.settings.AppSettingsData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettingsData: AppSettingsData
) : ViewModel() {

    fun onThemeSelected(theme: AppTheme) {
        viewModelScope.launch {
            appSettingsData.setTheme(theme)
        }
    }
}