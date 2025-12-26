package com.aper.app_database.session

import com.aper.app_database.datastore.PreferenceDataStore
import com.aper.core_domain.session.AppSessionData
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

    override suspend fun clearSessionData() {
        dataStore.clearToken()
        dataStore.clearLogin()
        dataStore.clearAvatar()
        dataStore.clearLoginState()
    }
}
