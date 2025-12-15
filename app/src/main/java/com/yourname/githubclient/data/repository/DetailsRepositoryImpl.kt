package com.yourname.githubclient.data.repository

import com.yourname.githubclient.data.local.RepositoryDao
import com.yourname.githubclient.data.remote.api.GithubApi
import com.yourname.githubclient.data.remote.model.toDomain
import com.yourname.githubclient.data.local.model.toDomain
import com.yourname.githubclient.data.remote.model.toEntity
import com.yourname.githubclient.domain.model.Repository
import com.yourname.githubclient.domain.repository.DetailsRepository
import com.yourname.githubclient.util.NetworkChecker

class DetailsRepositoryImpl(
    private val api: GithubApi,
    private val repoDao: RepositoryDao,
    private val networkChecker: NetworkChecker
) : DetailsRepository {

    override suspend fun getUserRepos(login: String): List<Repository> {
        return if (networkChecker.isOnline()) {
            val repos = api.getUserRepos(login)
            repoDao.clearRepos(login)
            repoDao.insertRepositories(repos.map { it.toEntity() })
            repos.map { it.toDomain() }

        } else {
            repoDao.getRepos(login)
                .map { it.toDomain() }
        }
    }
}
