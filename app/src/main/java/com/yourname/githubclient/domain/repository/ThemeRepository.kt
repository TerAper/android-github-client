package com.yourname.githubclient.domain.repository

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    val themeFlow: Flow<Boolean>
    suspend fun setTheme(isDark: Boolean)
}
