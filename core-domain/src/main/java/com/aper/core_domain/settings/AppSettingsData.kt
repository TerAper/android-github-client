package com.aper.core_domain.settings

import com.aper.core_domain.model.AppTheme
import kotlinx.coroutines.flow.Flow

interface AppSettingsData {
    fun observeTheme(): Flow<AppTheme>
    suspend fun setTheme(theme: AppTheme)
}
