package com.yourname.githubclient.domain.repository

import com.yourname.githubclient.domain.model.Repository
import com.yourname.githubclient.domain.model.User

interface DetailsRepository {
    suspend fun getUserRepos(login: String): List<Repository>
}
