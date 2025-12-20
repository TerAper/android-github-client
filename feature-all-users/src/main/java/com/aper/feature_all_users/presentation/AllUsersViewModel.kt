package com.aper.feature_all_users.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.core.model.User
import com.aper.feature_all_users.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllUsersViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private var currentPage = 0
    private val pageSize = 9
    private var isLoadingMore = false
    private var initialized = false

    fun loadInitial() {
        if (initialized) return
        initialized = true

        viewModelScope.launch {
            _loading.value = true
            try {
                val users = getAllUsersUseCase(0, pageSize)
                _users.value = users
                currentPage = 1
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadMore() {
        if (isLoadingMore || _loading.value) return
        isLoadingMore = true

        viewModelScope.launch {
            try {
                val users = getAllUsersUseCase(currentPage, pageSize)
                if (users.isNotEmpty()) {
                    _users.value += users
                    currentPage++
                }
            } finally {
                isLoadingMore = false
            }
        }
    }

    fun refresh() {
        initialized = false
        currentPage = 0
        _users.value = emptyList()
        loadInitial()
    }
}
