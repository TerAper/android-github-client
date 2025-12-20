package com.aper.feature_profile.data.event

sealed interface ProfileEvent {
    object NavigateToSettings : ProfileEvent
    object PickAvatar : ProfileEvent
}
