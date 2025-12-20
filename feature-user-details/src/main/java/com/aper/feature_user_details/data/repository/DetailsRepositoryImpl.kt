package com.aper.feature_user_details.data.repository

import com.aper.core.model.Repository
import com.aper.core.network.NetworkChecker
import com.aper.feature_user_details.domain.repository.DetailsRepository
import com.example.app_database.dao.RepositoryDao
import com.example.app_database.mapper.toDomain
import com.example.app_database.mapper.toEntity
import com.example.app_network.api.GithubApi
import com.example.app_network.mapper.toDomain
import javax.inject.Inject


class DetailsRepositoryImpl @Inject constructor(
    private val api: GithubApi,
    private val repoDao: RepositoryDao,
    private val networkChecker: NetworkChecker
) : DetailsRepository{

    override suspend fun getUserRepos(login: String): List<Repository> {
        return if (networkChecker.isOnline()) {
            val repos = api.getUserRepos(login).map { it.toDomain() }
            repoDao.clearRepos(login)
            repoDao.insertRepositories(repos.map { it.toEntity() })
            repos
        } else {
            repoDao.getRepos(login)
                .map { it.toDomain() }
        }
    }
}
