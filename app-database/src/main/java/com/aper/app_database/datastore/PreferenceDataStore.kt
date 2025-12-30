package com.aper.app_database.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.aper.core_android.security.CryptoManager
import com.aper.core_domain.model.AppTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "app_prefs")

@Singleton
class PreferenceDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cryptoManager: CryptoManager
) {

    private object Keys {
        val TOKEN = stringPreferencesKey("auth_token")
        val LOGIN = stringPreferencesKey("login")
        val THEME = stringPreferencesKey("app_theme")
        val AVATAR_URI = stringPreferencesKey("avatar_uri")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }


    val tokenFlow: Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[Keys.TOKEN]?.let { cryptoManager.decrypt(it) }
        }

    val loginFlow: Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[Keys.LOGIN]
        }

    val avatarFlow: Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[Keys.AVATAR_URI]
        }

    val isLoggedInFlow: Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[Keys.IS_LOGGED_IN] ?: false
        }

    val themeFlow: Flow<AppTheme> =
        context.dataStore.data.map { prefs ->
            val raw = prefs[Keys.THEME] ?: AppTheme.SYSTEM.name
            runCatching { AppTheme.valueOf(raw) }
                .getOrElse { AppTheme.SYSTEM }
        }


    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.TOKEN] = cryptoManager.encrypt(token)
        }
    }

    suspend fun saveLogin(login: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.LOGIN] = login
        }
    }

    suspend fun saveLoginState(isLoggedIn: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.IS_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun updateAvatar(uri: String?) {
        context.dataStore.edit { prefs ->
            prefs[Keys.AVATAR_URI] = uri.orEmpty()
        }
    }

    suspend fun setTheme(theme: AppTheme) {
        context.dataStore.edit { prefs ->
            prefs[Keys.THEME] = theme.name
        }
    }


    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.TOKEN)
        }
    }

    suspend fun clearLogin() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.LOGIN)
        }
    }

    suspend fun clearAvatar() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.AVATAR_URI)
        }
    }

    suspend fun clearLoginState() {
        context.dataStore.edit { prefs ->
            prefs[Keys.IS_LOGGED_IN] = false
        }
    }

    suspend fun clearTheme() {
        context.dataStore.edit { prefs ->
            prefs.remove(Keys.THEME)
        }
    }

}
