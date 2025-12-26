package com.aper.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.core.settings.AppSettingsData
import com.aper.core.util.asState
import com.aper.presentation.event.ProfileUiEvent
import com.aper.domain.usecase.GetAvatarUseCase
import com.aper.domain.usecase.GetUserNameUseCase
import com.aper.domain.usecase.LogoutUseCase
import com.aper.domain.usecase.SaveAvatarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val saveAvatarUseCase: SaveAvatarUseCase,
    getAvatarUseCase: GetAvatarUseCase,
    getUserNameUseCase: GetUserNameUseCase,
    appSettingsData: AppSettingsData

) : ViewModel() {

    val avatarUri = getAvatarUseCase()
        .asState(viewModelScope, null)

    val username = getUserNameUseCase()
        .asState(viewModelScope, "")

    private val _events = MutableSharedFlow<ProfileUiEvent>()
    val events = _events.asSharedFlow()

    fun onSettingsClick() {
        emitEvent(ProfileUiEvent.NavigateToSettings)
    }

    fun onAvatarClick() {
        emitEvent(ProfileUiEvent.PickAvatar)
    }

    fun onAvatarPicked(uri: Uri) {
        viewModelScope.launch {
            saveAvatarUseCase(uri.toString())
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

    private fun emitEvent(event: ProfileUiEvent) {
        viewModelScope.launch {
            _events.emit(event)
        }
    }
}
