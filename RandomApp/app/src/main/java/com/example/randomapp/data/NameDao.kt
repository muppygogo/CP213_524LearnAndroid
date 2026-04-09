package com.example.randomapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.randomapp.data.NameEntity

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