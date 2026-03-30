package com.example.randomapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {

    @Insert
    suspend fun insertAll(foods: List<FoodEntity>)

    @Query("SELECT * FROM foods")
    suspend fun getAllFoods(): List<FoodEntity>

    @Query("SELECT COUNT(*) FROM foods")
    suspend fun getCount(): Int
}