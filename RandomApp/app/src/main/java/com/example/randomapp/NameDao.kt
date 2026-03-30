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
    suspend fun getAllNames(): List<NameEntity>

    @Query("SELECT COUNT(*) FROM names")
    suspend fun getCount(): Int
}