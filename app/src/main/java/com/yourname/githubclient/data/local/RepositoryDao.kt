package com.yourname.githubclient.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repositories ORDER BY id ASC")
    fun getReposRx(): Single<List<RepositoryEntity>>

    @Query("SELECT * FROM repositories WHERE ownerLogin = :username")
    suspend fun getReposForUser(
        username: String
    ): List<RepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReposRx(repositories: List<RepositoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(
        repos: List<RepositoryEntity>
    )

    @Query("DELETE FROM repositories")
    fun clearReposRx()

    @Query("DELETE FROM repositories WHERE ownerLogin = :username")
    suspend fun clearUserRepos(username: String)
}
