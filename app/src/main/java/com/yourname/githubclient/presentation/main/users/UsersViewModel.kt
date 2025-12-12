package com.yourname.githubclient.presentation.main.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourname.githubclient.domain.model.User
import com.yourname.githubclient.domain.repository.UsersRepository
import com.yourname.githubclient.domain.usecase.users.GetAllUsersUseCase
import com.yourname.githubclient.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsersViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val usersRepository: UsersRepository
) : BaseViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private var currentPage = 0
    private val pageSize = 20
    private var isLoadingMore = false

    fun loadInitial() {
        viewModelScope.launch {
            setLoading(true)
            try {
                // show cached quickly (if any)
                val cached = usersRepository.getCachedUsers(limit = pageSize, offset = 0)
                if (cached.isNotEmpty()) {
                    _users.value = cached
                }

                // fetch fresh page 0
                currentPage = 0
                val fetched = getAllUsersUseCase.invoke(currentPage, pageSize)
                if (fetched.isNotEmpty()) {
                    _users.value = fetched
                    usersRepository.cacheUsers(fetched)
                    currentPage++
                }
            } catch (e: Exception) {
                setError("Failed to load users: ${e.message}")
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
                val fetched = getAllUsersUseCase.invoke(currentPage, pageSize)
                if (fetched.isNotEmpty()) {
                    val updated = _users.value + fetched
                    _users.value = updated
                    usersRepository.cacheUsers(fetched)
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
        currentPage = 0
        _users.value = emptyList()
        loadInitial()
    }

    class Factory(
        private val getAllUsersUseCase: GetAllUsersUseCase,
        private val usersRepository: UsersRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UsersViewModel(getAllUsersUseCase, usersRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
