package com.yourname.githubclient.domain.usecase

import com.yourname.githubclient.domain.repository.ThemeRepository

class UpdateThemeUseCase(private val repo: ThemeRepository) {
    suspend operator fun invoke(isDark: Boolean) = repo.setTheme(isDark)
}
