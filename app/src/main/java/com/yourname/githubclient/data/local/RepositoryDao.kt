package com.yourname.githubclient.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourname.githubclient.data.local.model.RepositoryEntity
import io.reactivex.rxjava3.core.Single


@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repositories WHERE profile = :login ORDER BY cachedOrder ASC LIMIT :limit OFFSET :offset")
    fun getReposRx(login: String, limit: Int, offset: Int): Single<List<RepositoryEntity>>

    @Query("SELECT * FROM repositories WHERE owner = :login")
    suspend fun getRepos(login: String ): List<RepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReposRx(repositories: List<RepositoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(
        repos: List<RepositoryEntity>
    )

    @Query("DELETE FROM repositories")
    fun clearReposRx()

    @Query("DELETE FROM repositories")
    suspend fun clearRepos()

    @Query("DELETE FROM repositories WHERE owner != :username")
    suspend fun clearAllReposExcept(username: String)

    @Query("DELETE FROM repositories WHERE owner = :username")
    suspend fun clearRepos(username: String)
}
