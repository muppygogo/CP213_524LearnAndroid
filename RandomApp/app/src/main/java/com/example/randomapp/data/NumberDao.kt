package com.example.randomapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.randomapp.data.NumberEntity

@Dao
interface NumberDao {

    @Insert
    suspend fun insertAll(numbers: List<NumberEntity>)

    @Query("SELECT * FROM numbers")
    suspend fun getAllNumbers(): List<NumberEntity>

    @Query("SELECT COUNT(*) FROM numbers")
    suspend fun getCount(): Int
}