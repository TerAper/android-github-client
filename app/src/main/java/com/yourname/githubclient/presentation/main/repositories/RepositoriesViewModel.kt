package com.yourname.githubclient.presentation.main.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yourname.githubclient.di.ServiceLocator.getUsernameUseCase
import com.yourname.githubclient.domain.model.Repository
import com.yourname.githubclient.domain.usecase.profile.GetUsernameUseCase
import com.yourname.githubclient.domain.usecase.repositories.GetUserRepositoriesUseCase
import com.yourname.githubclient.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoriesViewModel(
    private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase,
    getUsernameUseCase: GetUsernameUseCase
) : BaseViewModel() {

    val login = getUsernameUseCase()
    private val disposables = CompositeDisposable()

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories

    private var currentPage = 1
    private val perPage = 10
    private var initialized = false

    fun loadInitial() {
        if (initialized) return
        initialized = true
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading.value) return

        setLoading(true)

        val disposable = getUserRepositoriesUseCase(currentPage, perPage, login!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                _repositories.value = _repositories.value.orEmpty() + list
                currentPage++
                setLoading(false)
            }, {
                setLoading(false)
            })

        disposables.add(disposable)
    }

    fun refresh() {
        initialized = false
        currentPage = 1
        _repositories.value = emptyList()
        loadInitial()
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    class Factory(
        private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase,
        private  val getUsernameUseCase: GetUsernameUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepositoriesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RepositoriesViewModel(getUserRepositoriesUseCase,getUsernameUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
