package com.aper.presentation

import com.aper.domain.model.AllUsersUser

data class AllUsersUiState(
    val users: List<AllUsersUser> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false
)