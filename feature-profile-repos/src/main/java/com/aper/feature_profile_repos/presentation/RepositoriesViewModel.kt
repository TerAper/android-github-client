package com.aper.feature_profile_repos.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aper.core.model.AppTheme
import com.aper.core.model.Repository
import com.aper.core.sessionData.AppSessionData
import com.aper.core.sessionData.SessionDataKey
import com.aper.feature_profile_repos.domain.usecase.GetProfileRepositoriesUseCase
import com.aper.feature_profile_repos.domain.usecase.ObserveUsernameRxUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val getProfileRepositoriesUseCase: GetProfileRepositoriesUseCase,
    observeUsernameRxUseCase: ObserveUsernameRxUseCase,
    sessionData: AppSessionData
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private var currentLogin: String? = null

    val repositories = mutableStateOf<List<Repository>>(emptyList())
    val isRefreshing = mutableStateOf(false)

    val selectedTheme: StateFlow<AppTheme> =
        sessionData.observe(SessionDataKey.ThemeKey)
            .map { it ?: AppTheme.SYSTEM }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                AppTheme.SYSTEM
            )

    private var currentPage = 1
    private val perPage = 10
    private var isLoading = false

    init {
        observeUsernameRxUseCase.username
            .distinctUntilChanged()
            .subscribe { login ->
                currentLogin = login
                loadInitial()
            }
            .addTo(disposables)
    }

    fun loadInitial() {
        if (isLoading) return
        currentPage = 1
        repositories.value = emptyList()
        loadNextPage()
    }

    fun loadNextPage() {
        val login = currentLogin ?: return
        if (isLoading) return

        isLoading = true

        getProfileRepositoriesUseCase(currentPage, perPage, login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    repositories.value = repositories.value + list
                    currentPage++
                    isLoading = false
                    isRefreshing.value = false
                },
                {
                    isLoading = false
                    isRefreshing.value = false
                }
            )
            .addTo(disposables)
    }

    fun refresh() {
        isRefreshing.value = true
        loadInitial()
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
