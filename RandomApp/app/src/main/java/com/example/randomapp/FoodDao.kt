package com.example.randomapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: FoodEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<FoodEntity>)

    @Query("SELECT * FROM foods")
    suspend fun getAllFoods(): List<FoodEntity>

    @Query("SELECT COUNT(*) FROM foods")
    suspend fun getCount(): Int
}