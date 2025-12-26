package com.aper.presentation

import androidx.lifecycle.ViewModel
import com.aper.core.settings.AppSettingsData
import com.aper.domain.usecase.GetProfileReposUseCase
import com.aper.domain.usecase.ObserveUsernameRxUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileReposViewModel @Inject constructor(
    private val getProfileRepositoriesUseCase: GetProfileReposUseCase,
    observeUsernameRxUseCase: ObserveUsernameRxUseCase,
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private var currentLogin: String? = null
    private var currentPage = 1
    private val perPage = 10
    private var initialized = false
    private var isLoading = false

    private val _uiState = MutableStateFlow(ProfileReposUiState())
    val uiState: StateFlow<ProfileReposUiState> = _uiState.asStateFlow()

    init {
        observeUsernameRxUseCase.username
            .distinctUntilChanged()
            .subscribe { login ->
                currentLogin = login
                initialized = false
                loadInitial()
            }
            .addTo(disposables)
    }

    fun loadInitial() {
        if (initialized) return
        initialized = true

        currentPage = 1
        _uiState.value = ProfileReposUiState(isRefreshing = true)
        loadNextPage()
    }

    fun loadNextPage() {
        val login = currentLogin ?: return
        val state = _uiState.value

        if (isLoading || !state.hasMore) return

        isLoading = true
        _uiState.update { it.copy(isLoadingNext = true) }

        getProfileRepositoriesUseCase(currentPage, perPage, login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    _uiState.update {
                        it.copy(
                            repos = it.repos + list,
                            isRefreshing = false,
                            isLoadingNext = false,
                            hasMore = list.isNotEmpty()
                        )
                    }
                    if (list.isNotEmpty()) currentPage++
                    isLoading = false
                },
                {
                    isLoading = false
                    _uiState.update {
                        it.copy(
                            isRefreshing = false,
                            isLoadingNext = false
                        )
                    }
                }
            )
            .addTo(disposables)
    }

    fun refresh() {
        initialized = false
        currentPage = 1
        _uiState.value = ProfileReposUiState()
        loadInitial()
    }

    override fun onCleared() {
        disposables.clear()
    }
}
