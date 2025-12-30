package com.aper.presentation.event

sealed interface ProfileUiEvent {
    data object NavigateToSettings : ProfileUiEvent
    data object PickAvatar : ProfileUiEvent
}
