package com.aper.presentation

import androidx.compose.runtime.Immutable
import com.aper.domain.model.ProfileRepos

@Immutable
data class ProfileReposUiState(
    val repos: List<ProfileRepos> = emptyList(),
    val isRefreshing: Boolean = false,
    val isLoadingNext: Boolean = false,
    val hasMore: Boolean = true
)

