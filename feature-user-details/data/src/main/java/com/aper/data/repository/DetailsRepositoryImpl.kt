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
            val userRepos = api.getUserRepos(login)
            repoDao.clearRepos(login)
            val userReposEntity = userRepos.map { it.toEntity() }
            repoDao.insertRepositories(userReposEntity)
            userReposEntity.map { it.toDomain() }
        } else {
            repoDao.getRepos(login).map { it.toDomain() }
        }
    }
}
