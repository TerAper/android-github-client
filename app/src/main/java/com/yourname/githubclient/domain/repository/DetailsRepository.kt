package com.yourname.githubclient.domain.repository

import com.yourname.githubclient.domain.model.Repository
import com.yourname.githubclient.domain.model.UserDetails

interface DetailsRepository {
    suspend fun getUserDetails(username: String): UserDetails
    suspend fun getUserRepos(username: String): List<Repository>
}
