package com.aper.domain.usecase

import com.aper.domain.repository.AllUsersRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val repository: AllUsersRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int) =
        repository.getUsers(page, pageSize)
}
