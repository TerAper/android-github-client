package com.aper.app_database.settings

import com.aper.core.model.AppTheme
import com.aper.core.settings.AppSettingsData
import com.aper.app_database.datastore.PreferenceDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingsDataManager @Inject constructor(
    private val dataStore: PreferenceDataStore
) : AppSettingsData {

     override fun observeTheme(): Flow<AppTheme> = dataStore.themeFlow

    override suspend fun setTheme(theme: AppTheme) = dataStore.setTheme(theme)
}
