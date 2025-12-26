package com.aper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.core.model.AppTheme
import com.aper.core.settings.AppSettingsData
import com.aper.core.util.asState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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