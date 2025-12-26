package com.aper.data.repository

import com.aper.app_database.dao.RepositoryDao
import com.aper.core_android.network.NetworkChecker
import com.aper.data.api.UserReposApi
import com.aper.data.mapper.toDomain
import com.aper.data.mapper.toEntity
import com.aper.domain.model.UserRepos
import com.aper.domain.repository.DetailsRepository
import javax.inject.Inject


class DetailsRepositoryImpl @Inject constructor(
    private val api: UserReposApi,
    private val repoDao: RepositoryDao,
    private val networkChecker: NetworkChecker
) : DetailsRepository {

    override suspend fun getUserRepos(login: String): List<UserRepos> {
        return if (networkChecker.isOnline()) {

            val remoteRepos = api.getUserRepos(login)

            val entities = remoteRepos.map { it.toEntity() }

            repoDao.clearRepos(login)
            repoDao.insertRepositories(entities)

            entities.map { it.toDomain() }

        } else {
            repoDao.getRepos(login).map { it.toDomain() }
        }
    }
}
