package com.yourname.githubclient.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getUsers(limit: Int, offset: Int): List<UserEntity>

    @Query("SELECT * FROM users WHERE login = :username LIMIT 1")
    suspend fun getUserByLogin(username: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clearAll()

    @Query("SELECT id FROM users ORDER BY id DESC LIMIT 1")
    suspend fun getLastUserId(): Int?
}
