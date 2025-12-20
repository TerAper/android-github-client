package com.aper.feature_profile_repos.data.repository

import android.util.Log
import com.aper.core.model.Repository
import com.aper.core.network.NetworkChecker
import com.aper.feature_profile_repos.domain.repository.ProfileRepoRepository
import com.example.app_database.dao.RepositoryDao
import com.example.app_database.mapper.toDomain
import com.example.app_database.mapper.toEntity
import com.example.app_network.api.GithubApi
import com.example.app_network.mapper.toDomain
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ProfileRepoRepositoryImpl @Inject constructor(
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

        return if (networkChecker.isOnline()) {
            api.getUserRepositories(page, perPage)
                .subscribeOn(Schedulers.io())
                .flatMap { dtoList ->
                    val entities = dtoList.mapIndexed { index, dto ->
                        dto.toDomain().toEntity(cachedOrder = offset + index,login)
                    }

                    dao.insertReposRx(entities)
                        .ignoreElement()
                        .andThen(
                            Observable.just(
                                dtoList.map {
                                    it.toDomain() }
                            )
                        )
                }

        } else {
            dao.getReposRx(login, perPage, offset)
                .subscribeOn(Schedulers.io())
                .map { it.map { repo -> repo.toDomain() } }
                .toObservable()
        }
    }



    override fun clearAllRepositories() {
        dao.clearReposRx()
    }

}