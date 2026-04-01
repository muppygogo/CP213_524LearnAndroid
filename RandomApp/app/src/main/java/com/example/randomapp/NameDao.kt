package com.example.randomapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NameDao {

    @Insert
    suspend fun insert(name: NameEntity)

    @Insert
    suspend fun insertAll(names: List<NameEntity>)

    @Query("SELECT * FROM names")
    suspend fun getAll(): List<NameEntity>

    @Query("DELETE FROM names WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM names")
    suspend fun deleteAll()

    @Query("SELECT * FROM names WHERE name = :name LIMIT 1")
    suspend fun checkDuplicate(name: String): NameEntity?
}