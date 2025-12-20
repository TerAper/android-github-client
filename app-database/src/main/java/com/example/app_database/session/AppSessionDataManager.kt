package com.example.app_database.session

import com.aper.core.model.AppTheme
import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import com.example.app_database.datastore.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppSessionDataManager @Inject constructor(
    private val dataStore: PreferenceDataStore
) : AppSessionData {

    @Volatile private var token: String? = null
    @Volatile private var login: String? = null
    @Volatile private var avatar: String? = null
    @Volatile private var isLoggedIn: Boolean = false
    @Volatile private var theme: AppTheme = AppTheme.SYSTEM

    init {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.tokenFlow.collect { token = it }
        }
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.loginFlow.collect { login = it }
        }
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.avatarFlow.collect { avatar = it }
        }
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.isLoggedInFlow.collect { isLoggedIn = it }
        }
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.themeFlow.collect { theme = it }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: SessionDataKey<T>): T? =
        when (key) {
            SessionDataKey.TokenKey -> token as T?
            SessionDataKey.LoginKey -> login as T?
            SessionDataKey.AvatarKey -> avatar as T?
            SessionDataKey.IsLoggedInKey -> isLoggedIn as T
            SessionDataKey.ThemeKey -> theme as T
        }

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
        token = null
        login = null
        avatar = null
        isLoggedIn = false

        dataStore.clearToken()
        dataStore.clearLogin()
        dataStore.clearAvatar()
        dataStore.clearLoginState()
    }

    override suspend fun <T> set(key: SessionDataKey<T>, value: T) {
        when (key) {
            SessionDataKey.TokenKey -> {
                token = value as String
                dataStore.saveToken(token!!)
            }

            SessionDataKey.LoginKey -> {
                login = value as String
                dataStore.saveLogin(login!!)
            }

            SessionDataKey.AvatarKey -> {
                avatar = value as String?
                dataStore.updateAvatar(avatar)
            }

            SessionDataKey.IsLoggedInKey -> {
                isLoggedIn = value as Boolean
                dataStore.saveLoginState(isLoggedIn)
            }

            SessionDataKey.ThemeKey -> {
                theme = value as AppTheme
                dataStore.setTheme(theme)
            }
        }
    }
}
