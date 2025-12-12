package com.yourname.githubclient.presentation.main.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yourname.githubclient.domain.model.Repository
import com.yourname.githubclient.domain.usecase.repositories.GetUserRepositoriesUseCase
import com.yourname.githubclient.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoriesViewModel(
    private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase
) : BaseViewModel() {

    private val disposables = CompositeDisposable()

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories

    private var currentPage = 1
    private val perPage = 10

    fun loadNextPage() {
        if (isLoading.value) return
        setLoading(true)

        val disposable = getUserRepositoriesUseCase.execute(currentPage, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                val existing = _repositories.value.orEmpty()
                _repositories.value = existing + list
                currentPage++
                setLoading(false)
            }, { error ->
                setError(error.message ?: "Unknown error")
                setLoading(false)
            })

        disposables.add(disposable)
    }

    fun refresh() {
        currentPage = 1
        _repositories.value = emptyList()
        loadNextPage()
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

    class Factory(
        private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepositoriesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RepositoriesViewModel(getUserRepositoriesUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
