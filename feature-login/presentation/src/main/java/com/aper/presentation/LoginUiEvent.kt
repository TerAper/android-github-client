package com.aper.presentation

import com.aper.presentation.util.UiText

sealed interface LoginUiEvent {
    data class ShowSnackbar(val message: UiText) : LoginUiEvent
}
