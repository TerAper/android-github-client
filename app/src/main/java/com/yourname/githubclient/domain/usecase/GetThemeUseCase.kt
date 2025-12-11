package com.yourname.githubclient.domain.usecase

import com.yourname.githubclient.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase(private val repo: ThemeRepository) {
    operator fun invoke(): Flow<Boolean> = repo.themeFlow
}
