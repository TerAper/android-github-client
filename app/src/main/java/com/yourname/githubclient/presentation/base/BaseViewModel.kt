package com.yourname.githubclient.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun setLoading(value: Boolean) {
        _isLoading.value = value
    }

    fun setError(msg: String) {
        _errorMessage.value = msg
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
