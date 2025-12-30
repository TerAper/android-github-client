package com.aper.data.repository

import com.aper.app_database.dao.RepositoryDao
import com.aper.core_android.network.NetworkChecker
import com.aper.data.api.ProfileReposApi
import com.aper.data.mapper.toEntity
import com.aper.data.mapper.toDomain
import com.aper.domain.model.ProfileRepos
import com.aper.domain.repasitory.ProfileRepoRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ProfileRepoRepositoryImpl @Inject constructor(
    private val api: ProfileReposApi,
    private val dao: RepositoryDao,
    private val networkChecker: NetworkChecker
) : ProfileRepoRepository {

    override fun getProfileRepositories(
        page: Int,
        perPage: Int,
        login: String
    ): Observable<List<ProfileRepos>> {

        val offset = (page - 1) * perPage

        return if (networkChecker.isOnline()) {
            api.getUserRepositories(page, perPage)
                .subscribeOn(Schedulers.io())
                .flatMap { dtoList ->
                    val entities = dtoList.mapIndexed { index, dto ->
                        dto.toEntity(cachedOrder = offset + index,login)
                    }

                    dao.insertReposRx(entities)
                        .ignoreElement()
                        .andThen(
                            Observable.just(
                                entities.map { it.toDomain() }
                            )
                        )
                }

        } else {
            dao.getReposRx(login, perPage, offset)
                .subscribeOn(Schedulers.io())
                .map { it.map { repo -> repo.toDomain()} }
                .toObservable()
        }
    }



    override fun clearAllRepositories() {
        dao.clearReposRx()
    }

}