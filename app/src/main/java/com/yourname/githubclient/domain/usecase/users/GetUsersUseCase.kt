package com.yourname.githubclient.domain.usecase.users

import com.yourname.githubclient.domain.model.User
import com.yourname.githubclient.domain.repository.UsersRepository

class GetUsersUseCase(
    private val repository: UsersRepository
) {
    suspend operator fun invoke(page: Int, pageSize: Int): List<User> {

        return repository.getUsers(page,pageSize)
    }
}
