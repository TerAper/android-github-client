package com.example.app_database.session

import com.aper.core.model.AppTheme
import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import com.example.app_database.datastore.PreferenceDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSessionDataManager @Inject constructor(
    private val dataStore: PreferenceDataStore
) : AppSessionData {



    @Suppress("UNCHECKED_CAST")
    override fun <T> observe(key: SessionDataKey<T>): Flow<T?> =
        when (key) {
            SessionDataKey.TokenKey ->
                dataStore.tokenFlow.map { it as T? }

            SessionDataKey.LoginKey ->
                dataStore.loginFlow.map { it as T? }

            SessionDataKey.AvatarKey ->
                dataStore.avatarFlow.map { it as T? }

            SessionDataKey.IsLoggedInKey ->
                dataStore.isLoggedInFlow.map { it as T }

            SessionDataKey.ThemeKey ->
                dataStore.themeFlow.map { it as T }
        }

    override suspend fun clearSessionData() {
        dataStore.clearToken()
        dataStore.clearLogin()
        dataStore.clearAvatar()
        dataStore.clearLoginState()
    }

    override suspend fun <T> set(key: SessionDataKey<T>, value: T) {
        when (key) {
            SessionDataKey.TokenKey -> {
                dataStore.saveToken(value as String)
            }

            SessionDataKey.LoginKey -> {
                dataStore.saveLogin(value as String)
            }

            SessionDataKey.AvatarKey -> {
                dataStore.updateAvatar(value as String)
            }

            SessionDataKey.IsLoggedInKey -> {
                dataStore.saveLoginState(value as Boolean)
            }

            SessionDataKey.ThemeKey -> {
                dataStore.setTheme(value as AppTheme)
            }
        }
    }
}
