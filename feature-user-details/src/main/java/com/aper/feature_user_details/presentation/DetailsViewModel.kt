package com.yourname.githubclient.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aper.core.model.Repository
import com.aper.feature_user_details.domain.usecase.GetUserReposUseCaseForDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getUserRepos: GetUserReposUseCaseForDetails
) : ViewModel() {

    private val _repos = MutableStateFlow<List<Repository>>(emptyList())
    val repos: StateFlow<List<Repository>> = _repos.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()


    fun load(login: String) {
        viewModelScope.launch {
            runCatching {
                _loading.value = true
                getUserRepos(login)
            }.onSuccess {
                _repos.value = it
            }.onFailure {

            }
            _loading.value = false
        }
    }
}
