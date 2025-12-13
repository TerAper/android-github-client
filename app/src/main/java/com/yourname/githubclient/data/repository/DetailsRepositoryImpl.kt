package com.yourname.githubclient.data.repository

import android.content.Context
import com.yourname.githubclient.data.local.RepositoryDao
import com.yourname.githubclient.data.local.UserDao
import com.yourname.githubclient.data.remote.api.GithubApi
import com.yourname.githubclient.data.remote.model.toDomain
import com.yourname.githubclient.data.local.toDomain
import com.yourname.githubclient.data.remote.model.toEntity
import com.yourname.githubclient.domain.model.Repository
import com.yourname.githubclient.domain.model.UserDetails
import com.yourname.githubclient.domain.repository.DetailsRepository
import com.yourname.githubclient.util.isOnline

class DetailsRepositoryImpl(
    private val api: GithubApi,
    private val userDao: UserDao,
    private val repoDao: RepositoryDao,
    private val context: Context
) : DetailsRepository {

    override suspend fun getUserDetails(username: String): UserDetails {
        return if (context.isOnline()) {
            api.getUserDetails(username)
                .toDomain()
        } else {

            val user = userDao.getUserByLogin(username)
                ?: throw IllegalStateException("User not cached")

            UserDetails(
                username = user.login,
                name = null,
                avatarUrl = user.avatarUrl,
                bio = null,
                followers = 0,
                following = 0
            )
        }
    }


    override suspend fun getUserRepos(username: String): List<Repository> {
        return if (context.isOnline()) {
            val repos = api.getUserRepos(username)

            // Save for offline use
            repoDao.clearUserRepos(username)
            repoDao.insertRepositories(repos.map { it.toEntity() })

            repos.map { it.toDomain() }
        } else {
            repoDao.getReposForUser(username)
                .map { it.toDomain() }
        }
    }
}
