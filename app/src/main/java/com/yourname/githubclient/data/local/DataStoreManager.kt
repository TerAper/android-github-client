package com.yourname.githubclient.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore("app_prefs")

class DataStoreManager(context: Context) {
    private val appContext = context.applicationContext
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_LOGIN = stringPreferencesKey("login")
        private val KEY_THEME = booleanPreferencesKey("is_dark_mode")
        private val KEY_AVATAR_URI = stringPreferencesKey("avatar_uri")
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }
    var userName:String? = null
    val token: Flow<String?> = appContext.dataStore.data.map {it[KEY_TOKEN] }
    val login: Flow<String?> = appContext.dataStore.data.map { it[KEY_LOGIN] }
    val themeFlow: Flow<Boolean> = appContext.dataStore.data.map { it[KEY_THEME] ?: false }
    val avatarUriFlow: Flow<String?> = appContext.dataStore.data.map { it[KEY_AVATAR_URI] }
    val isLoggedInFlow: Flow<Boolean> = appContext.dataStore.data.map { it[KEY_IS_LOGGED_IN] ?: false }

    init {
        scope.launch {
            appContext.dataStore.data
                .map { it[KEY_LOGIN] }
                .collect { value ->
                    userName = value
                }
        }
    }

    suspend fun saveUser(token: String, login: String, avatarUrl: String?) {
        appContext.dataStore.edit {
            it[KEY_TOKEN] = token
            it[KEY_LOGIN] = login
            it[KEY_AVATAR_URI] = avatarUrl ?: ""
            it[KEY_IS_LOGGED_IN] = true
        }
    }


    suspend fun clearLoginState() {
        appContext.dataStore.edit {
            it.remove(KEY_TOKEN)
            it.remove(KEY_LOGIN)
            it.remove(KEY_AVATAR_URI)
            it[KEY_IS_LOGGED_IN] = false
        }
    }

    suspend fun saveTheme(isDark: Boolean) {
        appContext.dataStore.edit { it[KEY_THEME] = isDark }
    }

    suspend fun saveAvatar(uri: String) {
        appContext.dataStore.edit { it[KEY_AVATAR_URI] = uri }
    }

    suspend fun clearAvatar() {
        appContext.dataStore.edit { it.remove(KEY_AVATAR_URI) }
    }
}
