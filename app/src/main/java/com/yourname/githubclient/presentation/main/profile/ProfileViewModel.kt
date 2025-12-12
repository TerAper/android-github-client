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
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val saveAvatarUseCase: SaveAvatarUseCase,
    private val clearAvatarUseCase: ClearAvatarUseCase,
    getAvatarUseCase: GetAvatarUseCase,
    getUsernameUseCase: GetUsernameUseCase
) : BaseViewModel() {

    val username = getUsernameUseCase()
    val avatarUri = getAvatarUseCase()

    fun saveAvatar(uri: Uri) {
        viewModelScope.launch {
            saveAvatarUseCase(uri)
        }
    }

    suspend fun logout() {
        clearAvatarUseCase()
        logoutUseCase()
    }
}

class ProfileViewModelFactory(
    private val logoutUseCase: LogoutUseCase,
    private val saveAvatarUseCase: SaveAvatarUseCase,
    private val clearAvatarUseCase: ClearAvatarUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
    private val getUsernameUseCase: GetUsernameUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
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
