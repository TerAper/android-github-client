package com.yourname.githubclient.domain.usecase.users

import com.yourname.githubclient.domain.repository.UsersRepository

class GetAllUsersUseCase(private val repo: UsersRepository) {

    suspend operator fun invoke(page: Int, pageSize: Int) =
        repo.getUsers(page, pageSize)
}
