package com.aper.presentation

import com.aper.domain.model.ProfileRepos

data class ProfileReposUiState(
    val repos: List<ProfileRepos> = emptyList(),
    val isRefreshing: Boolean = false,
    val isLoadingNext: Boolean = false,
    val hasMore: Boolean = true
)

