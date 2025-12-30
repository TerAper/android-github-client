package com.aper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllUsersViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AllUsersUiState())
    val uiState: StateFlow<AllUsersUiState> = _uiState

    private var currentPage = 0
    private val pageSize = 9
    private var initialized = false

    fun loadInitial() {
        if (initialized) return
        initialized = true

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val users = getAllUsersUseCase(currentPage, pageSize)
                _uiState.value = AllUsersUiState(
                    users = users,
                    isLoading = false
                )
                currentPage++
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun loadMore() {
        val state = _uiState.value
        if (state.isLoading || state.isLoadingMore) return

        _uiState.value = state.copy(isLoadingMore = true)

        viewModelScope.launch {
            try {
                val users = getAllUsersUseCase(currentPage, pageSize)
                if (users.isNotEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        users = state.users + users,
                        isLoadingMore = false
                    )
                    currentPage++
                } else {
                    _uiState.value = _uiState.value.copy(isLoadingMore = false)
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoadingMore = false)
            }
        }
    }

    fun refresh() {
        initialized = false
        currentPage = 0
        _uiState.value = AllUsersUiState()
        loadInitial()
    }
}
