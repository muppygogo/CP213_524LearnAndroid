package com.example.randomapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.randomapp.data.local.entity.MealEntity

@Dao
interface MealDao {

    @Insert
    suspend fun insert(meal: MealEntity)

    @Update
    suspend fun update(meal: MealEntity)

    @Delete
    suspend fun delete(meal: MealEntity)

    @Query("SELECT * FROM meals")
    suspend fun getAllMeals(): List<MealEntity>

    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getMealById(id: Int): MealEntity?

    @Query("SELECT * FROM meals WHERE category_id = :categoryId")
    suspend fun getMealsByCategory(categoryId: Int): List<MealEntity>
}
