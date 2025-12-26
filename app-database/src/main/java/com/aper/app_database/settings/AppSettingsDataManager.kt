package com.aper.app_database.settings

import com.aper.core_domain.settings.AppSettingsData
import com.aper.app_database.datastore.PreferenceDataStore
import com.aper.core_domain.model.AppTheme
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
