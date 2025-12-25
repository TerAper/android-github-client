package com.aper.presentation

import com.aper.domain.model.UserRepos

data class DetailsUiState(
    val repos: List<UserRepos> = emptyList(),
    val isLoading: Boolean = false
)
