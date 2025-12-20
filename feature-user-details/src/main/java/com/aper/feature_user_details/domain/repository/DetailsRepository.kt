package com.aper.feature_user_details.domain.repository

import com.aper.core.model.Repository

interface DetailsRepository {
    suspend fun getUserRepos(login: String): List<Repository>
}
