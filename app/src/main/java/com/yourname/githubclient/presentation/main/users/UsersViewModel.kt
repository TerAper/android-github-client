package com.yourname.githubclient.presentation.main.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourname.githubclient.domain.model.User
import com.yourname.githubclient.domain.usecase.users.GetUsersUseCase
import com.yourname.githubclient.domain.usecase.users.StartUsersSessionUseCase
import com.yourname.githubclient.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val getUsersUseCase: GetUsersUseCase,
    private val startUsersSessionUseCase: StartUsersSessionUseCase,

    ) : BaseViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private var currentPage = 0
    private val pageSize = 9
    private var isLoadingMore = false
    private var initialized = false


    fun loadInitial() {
        if (initialized) return
        initialized = true

        viewModelScope.launch {
            setLoading(true)
            try {
                startUsersSessionUseCase()
                val users = getUsersUseCase(currentPage, pageSize)
                _users.value = users
                currentPage++
            } catch (e: Exception) {
                setError(e.message ?: "Error")
            } finally {
                setLoading(false)
            }
        }
    }

    fun loadMore() {
        if (isLoadingMore) return
        isLoadingMore = true

        viewModelScope.launch {
            try {
                val users = getUsersUseCase(currentPage, pageSize)
                if (users.isNotEmpty()) {
                    _users.value = _users.value + users
                    currentPage++
                }
            } catch (e: Exception) {
                setError("Pagination failed: ${e.message}")
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


    class Factory(
        private val getAllUsersUseCase: GetUsersUseCase,
        private val startUsersSessionUseCase: StartUsersSessionUseCase,


        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UsersViewModel(getAllUsersUseCase,startUsersSessionUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
