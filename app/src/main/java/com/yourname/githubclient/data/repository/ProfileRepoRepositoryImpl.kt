package com.yourname.githubclient.data.repository

import android.util.Log
import com.yourname.githubclient.data.local.RepositoryDao
import com.yourname.githubclient.data.local.model.toDomain
import com.yourname.githubclient.data.remote.api.GithubApi
import com.yourname.githubclient.data.remote.model.toDomain
import com.yourname.githubclient.data.remote.model.toEntity
import com.yourname.githubclient.domain.model.Repository
import com.yourname.githubclient.domain.repository.ProfileRepoRepository
import com.yourname.githubclient.util.NetworkChecker
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


class ProfileRepoRepositoryImpl(
    private val api: GithubApi,
    private val dao: RepositoryDao,
    private val networkChecker: NetworkChecker
) : ProfileRepoRepository {

    override fun getProfileRepositories(
        page: Int,
        perPage: Int,
        login: String
    ): Observable<List<Repository>> {

        val offset = (page - 1) * perPage

        return Observable.defer {
            if (networkChecker.isOnline()) {
                Log.e("ACV","ll")

                api.getUserRepositories(page, perPage)
                    .subscribeOn(Schedulers.io())
                    .doOnNext { dtoList ->
                        val startOrder = offset
                        val entities = dtoList.mapIndexed { index, dto ->
                            dto.toEntity(cachedOrder = startOrder + index,login)
                        }
                        dao.insertReposRx(entities)
                    }
                    .map { it.map { dto -> dto.toDomain() } }

            } else {
                dao.getReposRx(login, perPage, offset) // Use new paged DAO method
                    .subscribeOn(Schedulers.io())
                    .map { it.map { e -> e.toDomain() } }
                    .toObservable()
            }
        }
    }

    override suspend fun clearAllRepositories() {
        dao.clearRepos()
    }

}



