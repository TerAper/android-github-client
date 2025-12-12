package com.yourname.githubclient.data.repository

import android.content.Context
import com.yourname.githubclient.data.local.RepositoryDao
import com.yourname.githubclient.data.local.toDomain
import com.yourname.githubclient.data.remote.api.GithubApi
import com.yourname.githubclient.domain.model.Repository
import com.yourname.githubclient.domain.repository.RepositoriesRepository
import com.yourname.githubclient.util.isOnline
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


class RepositoriesRepositoryImpl(
    private val api: GithubApi,
    private val dao: RepositoryDao,
    private val context: Context
) : RepositoriesRepository {

    override fun getUserRepositories(page: Int, perPage: Int): Observable<List<Repository>> {
        return Observable.defer {
            if (context.isOnline() ) {
                api.getUserRepositories(page, perPage)
                    .subscribeOn(Schedulers.io())
                    .doOnNext { dtoList ->
                        dao.insertRepositories(dtoList.map { it.toEntity() })
                    }
                    .map { dtoList -> dtoList.map { it.toDomain() } }
                    .onErrorResumeNext { _: Throwable ->
                        dao.getAllRepositories()
                            .subscribeOn(Schedulers.io())
                            .map { entities ->
                                val from = (page - 1) * perPage
                                val to = minOf(from + perPage, entities.size)
                                if (from >= entities.size) emptyList()
                                else entities.subList(from, to).map { it.toDomain() }
                            }
                            .toObservable()
                    }
            } else {
                dao.getAllRepositories()
                    .subscribeOn(Schedulers.io())
                    .map { entities ->
                        val from = (page - 1) * perPage
                        val to = minOf(from + perPage, entities.size)
                        if (from >= entities.size) emptyList()
                        else entities.subList(from, to).map { it.toDomain() }
                    }
                    .toObservable()
            }
        }
    }
}
