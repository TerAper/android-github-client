package com.aper.presentation

sealed interface LoginUiEvent {
    data class ShowSnackbar(val message: String) : LoginUiEvent
}
