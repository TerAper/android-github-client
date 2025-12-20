package com.aper.feature_profile.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.core.model.AppTheme
import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import com.aper.feature_profile.data.event.ProfileEvent
import com.aper.feature_profile.domain.usecase.GetAvatarUseCase
import com.aper.feature_profile.domain.usecase.GetLoginUseCase
import com.aper.feature_profile.domain.usecase.LogoutUseCase
import com.aper.feature_profile.domain.usecase.SaveAvatarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val saveAvatarUseCase: SaveAvatarUseCase,
    getAvatarUseCase: GetAvatarUseCase,
    getUsernameUseCase: GetLoginUseCase,
    sessionData: AppSessionData

) : ViewModel() {

    val avatarUri = getAvatarUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val username = getUsernameUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "")

    private val _events = MutableSharedFlow<ProfileEvent>()
    val events = _events.asSharedFlow()

    val selectedTheme: StateFlow<AppTheme> =
        sessionData.observe(SessionDataKey.ThemeKey)
            .map { it ?: AppTheme.SYSTEM }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                AppTheme.SYSTEM
            )

    fun onSettingsClick() {
        emitEvent(ProfileEvent.NavigateToSettings)
    }

    fun onAvatarClick() {
        emitEvent(ProfileEvent.PickAvatar)
    }

    fun onAvatarPicked(uri: Uri) {
        viewModelScope.launch {
            saveAvatarUseCase(uri)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    private fun emitEvent(event: ProfileEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }
}
