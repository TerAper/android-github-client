package com.yourname.githubclient.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourname.githubclient.domain.model.Repository
import com.yourname.githubclient.domain.model.User
import com.yourname.githubclient.domain.usecase.details.GetUserReposUseCaseForDetails
import com.yourname.githubclient.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getUserReposUseCase: GetUserReposUseCaseForDetails
) : BaseViewModel() {

    private val _repos = MutableStateFlow<List<Repository>>(emptyList())
    val repos: StateFlow<List<Repository>> = _repos

    fun load(login: String) {
        viewModelScope.launch {
            try {
                setLoading(true)
                val repos = getUserReposUseCase(login)
                _repos.value = repos

            } catch (e: Exception) {
                setError("Failed: ${e.message}")
            } finally {
                setLoading(false)
            }
        }
    }
    class Factory(
        private val getUserReposUseCase: GetUserReposUseCaseForDetails
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(getUserReposUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
