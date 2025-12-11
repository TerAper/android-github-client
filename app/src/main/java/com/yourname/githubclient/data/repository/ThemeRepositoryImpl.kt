package com.yourname.githubclient.data.repository

import com.yourname.githubclient.data.local.DataStoreManager
import com.yourname.githubclient.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow

class ThemeRepositoryImpl(
    private val dataStore: DataStoreManager
) : ThemeRepository {

    override val themeFlow: Flow<Boolean>
        get() = dataStore.themeFlow

    override suspend fun setTheme(isDark: Boolean) {
        dataStore.saveTheme(isDark)
    }
}
