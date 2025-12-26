package com.aper.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

