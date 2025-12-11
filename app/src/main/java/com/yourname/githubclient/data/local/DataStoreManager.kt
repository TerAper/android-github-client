package com.yourname.githubclient.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("app_prefs")

class DataStoreManager(context: Context) {
    private val appContext = context.applicationContext

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_THEME = booleanPreferencesKey("is_dark_mode")
    }

    val token: Flow<String?> = appContext.dataStore.data.map { prefs ->
        prefs[KEY_TOKEN]
    }

    val themeFlow: Flow<Boolean> = appContext.dataStore.data.map { prefs ->
        prefs[KEY_THEME] ?: false
    }

    suspend fun saveToken(token: String) {
        appContext.dataStore.edit { it[KEY_TOKEN] = token }
    }

    suspend fun saveTheme(isDark: Boolean) {
        appContext.dataStore.edit { it[KEY_THEME] = isDark }
    }

    suspend fun clearToken() {
        appContext.dataStore.edit { it.remove(KEY_TOKEN) }
    }



    suspend fun clearAllExceptTheme() {
        appContext.dataStore.edit { prefs ->
            val theme = prefs[KEY_THEME] ?: false
            prefs.clear()
            prefs[KEY_THEME] = theme
        }
    }
}
