package com.example.randomapp.data.lacal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.randomapp.data.lacal.entity.NumberEntity

@Dao
interface NumberDao {

    @Insert
    suspend fun insertAll(numbers: List<NumberEntity>)

    @Query("SELECT * FROM numbers")
    suspend fun getAll(): List<NumberEntity>

    @Query("DELETE FROM numbers")
    suspend fun deleteAll()

    @Query("DELETE FROM numbers WHERE value = :value")
    suspend fun deleteByValue(value: Int)
}