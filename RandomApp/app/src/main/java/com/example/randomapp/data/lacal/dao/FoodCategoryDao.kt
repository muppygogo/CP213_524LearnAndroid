package com.example.randomapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.randomapp.data.local.entity.FoodCategory

@Dao
interface FoodCategoryDao {

    @Insert
    suspend fun insert(category: FoodCategory)

    @Update
    suspend fun update(category: FoodCategory)

    @Delete
    suspend fun delete(category: FoodCategory)

    @Query("SELECT * FROM food_categories")
    suspend fun getAllCategories(): List<FoodCategory>

    @Query("SELECT * FROM food_categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): FoodCategory?
}
