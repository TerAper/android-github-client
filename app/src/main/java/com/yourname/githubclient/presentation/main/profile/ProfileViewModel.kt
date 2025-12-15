package com.yourname.githubclient.presentation.main.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourname.githubclient.domain.usecase.login.LogoutUseCase
import com.yourname.githubclient.domain.usecase.profile.ClearAvatarUseCase
import com.yourname.githubclient.domain.usecase.profile.GetAvatarUseCase
import com.yourname.githubclient.domain.usecase.profile.GetUsernameUseCase
import com.yourname.githubclient.domain.usecase.profile.SaveAvatarUseCase
import com.yourname.githubclient.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val saveAvatarUseCase: SaveAvatarUseCase,
    private val clearAvatarUseCase: ClearAvatarUseCase,
    getAvatarUseCase: GetAvatarUseCase,
    getUsernameUseCase: GetUsernameUseCase
) : BaseViewModel() {
    private val _logoutEvent = MutableSharedFlow<Unit>()
    val logoutEvent = _logoutEvent

    val login = getUsernameUseCase()
    val avatarUri = getAvatarUseCase()

    fun saveAvatar(uri: Uri) {
        viewModelScope.launch {
            saveAvatarUseCase(uri)
        }
    }

    fun logout() {
        viewModelScope.launch {
            clearAvatarUseCase()
            logoutUseCase()
            _logoutEvent.emit(Unit)
        }
    }

    class Factory(
        private val logoutUseCase: LogoutUseCase,
        private val saveAvatarUseCase: SaveAvatarUseCase,
        private val clearAvatarUseCase: ClearAvatarUseCase,
        private val getAvatarUseCase: GetAvatarUseCase,
        private val getUsernameUseCase: GetUsernameUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(
                    logoutUseCase,
                    saveAvatarUseCase,
                    clearAvatarUseCase,
                    getAvatarUseCase,
                    getUsernameUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}