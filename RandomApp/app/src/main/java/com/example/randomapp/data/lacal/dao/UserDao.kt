package com.example.randomapp.data.lacal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.randomapp.data.lacal.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserEntity?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUserByEmail(email: String)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("""
        DELETE FROM users
        WHERE id NOT IN (
            SELECT MIN(id)
            FROM users
            GROUP BY email
        )
    """)
    suspend fun deleteDuplicateUsersKeepOne()
}