package com.aper.domain.repository

import com.aper.domain.model.UserRepos

interface DetailsRepository {
    suspend fun getUserRepos(login: String): List<UserRepos>
}
