package com.aper.core.settings

import com.aper.core.model.AppTheme
import kotlinx.coroutines.flow.Flow

interface AppSettingsData {
    fun observeTheme(): Flow<AppTheme>
    suspend fun setTheme(theme: AppTheme)
}
