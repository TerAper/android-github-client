package com.aper.core.settings

import com.aper.core.model.AppTheme
import kotlinx.coroutines.flow.Flow

sealed interface SettingsDataKey<T> {

    val observe: (AppSettingsData) -> Flow<T>
    val save: suspend (AppSettingsData, T) -> Unit

    data object ThemeKey : SettingsDataKey<AppTheme> {
        override val observe = { it: AppSettingsData -> it.observeTheme() }
        override val save: suspend (AppSettingsData, AppTheme) -> Unit = { s: AppSettingsData, v: AppTheme -> s.setTheme(v) }
    }

}
