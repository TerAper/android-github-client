package com.yourname.githubclient.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface RepositoryDao {
    @Query("SELECT * FROM repositories ORDER BY id ASC")
    fun getAllRepositories(): Single<List<RepositoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(repositories: List<RepositoryEntity>)

    @Query("DELETE FROM repositories")
    fun clearRepositories()
}
