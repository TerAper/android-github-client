package com.yourname.githubclient.data.repository

import com.yourname.githubclient.data.local.UserDao
import com.yourname.githubclient.data.local.UserEntity
import com.yourname.githubclient.data.remote.api.GithubApi
import com.yourname.githubclient.domain.model.User
import com.yourname.githubclient.domain.repository.UsersRepository

class UsersRepositoryImpl(
    private val api: GithubApi,
    private val dao: UserDao
) : UsersRepository {

    override suspend fun getUsers(page: Int, pageSize: Int): List<User> {
        // GitHub pagination: since = lastUserId (cursor). For page==0 use 0
        val since = if (page == 0) 0 else (dao.getLastUserId() ?: 0)

        return try {
            val apiUsers = api.getAllUsers(since = since, perPage = pageSize)

            val entities = apiUsers.map {
                UserEntity(
                    id = it.id,
                    login = it.login,
                    avatarUrl = it.avatar_url
                )
            }

            // insert into DB
            dao.insertUsers(entities)

            // map to domain
            entities.map { e -> User(e.id, e.login, e.avatarUrl) }
        } catch (e: Exception) {
            // offline / network error -> return cached users using limit/offset logic
            val offset = page * pageSize
            dao.getUsers(pageSize, offset).map { ent ->
                User(ent.id, ent.login, ent.avatarUrl)
            }
        }
    }

    override suspend fun cacheUsers(users: List<User>) {
        val entities = users.map { UserEntity(it.id, it.username, it.avatarUrl) }
        dao.insertUsers(entities)
    }

    override suspend fun getCachedUsers(limit: Int, offset: Int): List<User> {
        return dao.getUsers(limit, offset).map { ent ->
            User(ent.id, ent.login, ent.avatarUrl)
        }
    }

    override suspend fun getLastCachedUserId(): Int? {
        return dao.getLastUserId()
    }
}
