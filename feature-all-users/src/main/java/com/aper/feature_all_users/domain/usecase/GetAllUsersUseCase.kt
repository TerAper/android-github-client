package com.aper.feature_all_users.domain.usecase

import com.aper.core.model.User
import com.aper.feature_all_users.domain.repository.UsersRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val repository: UsersRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int): List<User> {

        return repository.getUsers(page, pageSize)
    }
}
