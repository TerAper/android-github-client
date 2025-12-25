package com.aper.app_database.session

import com.aper.core.session.AppSessionData
import com.aper.core.session.SessionDataKey
import com.aper.app_database.datastore.PreferenceDataStore
import javax.inject.Inject

class AppSessionDataManager @Inject constructor(
    private val dataStore: PreferenceDataStore
) : AppSessionData {

    override fun observeToken() = dataStore.tokenFlow
    override fun observeLogin() = dataStore.loginFlow
    override fun observeAvatar() = dataStore.avatarFlow
    override fun observeIsLoggedIn() = dataStore.isLoggedInFlow

    override suspend fun setToken(value: String) = dataStore.saveToken(value)
    override suspend fun setLogin(value: String) = dataStore.saveLogin(value)
    override suspend fun setAvatar(value: String?) = dataStore.updateAvatar(value)
    override suspend fun setIsLoggedIn(value: Boolean) = dataStore.saveLoginState(value)

    override fun <T> observe(key: SessionDataKey<T>) = key.observe(this)

    override suspend fun <T> set(key: SessionDataKey<T>, value: T) = key.save(this, value)

    override suspend fun clearSessionData() {
        dataStore.clearToken()
        dataStore.clearLogin()
        dataStore.clearAvatar()
        dataStore.clearLoginState()
    }
}
