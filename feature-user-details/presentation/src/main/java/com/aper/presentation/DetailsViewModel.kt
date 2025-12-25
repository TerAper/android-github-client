package com.aper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.domain.model.UserRepos
import com.aper.domain.usecase.GetUserReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getUserRepos: GetUserReposUseCase
) : ViewModel() {

    private var initialized = false

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun load(login: String) {
        if (initialized) return
        initialized = true

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val repos = getUserRepos(login)

            _uiState.value = DetailsUiState(
                repos = repos,
                isLoading = false
            )
        }
    }
}
