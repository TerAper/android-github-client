package com.aper.app_database.settings

import com.aper.core.model.AppTheme
import com.aper.core.settings.AppSettingsData
import com.aper.app_database.datastore.PreferenceDataStore
import com.aper.core.settings.SettingsDataKey
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSettingsDataManager @Inject constructor(
    private val dataStore: PreferenceDataStore
) : AppSettingsData {

     override fun observeTheme(): Flow<AppTheme> = dataStore.themeFlow

    override suspend fun setTheme(theme: AppTheme) = dataStore.setTheme(theme)

    override fun <T> observe(key: SettingsDataKey<T>) = key.observe(this)

    override suspend fun <T> set(key: SettingsDataKey<T>, value: T) = key.save(this,value)

}
