package com.aper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.domain.usecase.GetUserReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getUserRepos: GetUserReposUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun load(login: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                getUserRepos(login)
            }.onSuccess { repos ->
                _uiState.value = DetailsUiState(
                    repos = repos,
                    isLoading = false
                )
            }.onFailure {
                _uiState.value = DetailsUiState(
                    isLoading = false,
                )
            }
        }
    }
}
