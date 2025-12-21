package com.aper.feature_profile.data.event

sealed interface ProfileEvent {
    data object NavigateToSettings : ProfileEvent
    data object PickAvatar : ProfileEvent
}
