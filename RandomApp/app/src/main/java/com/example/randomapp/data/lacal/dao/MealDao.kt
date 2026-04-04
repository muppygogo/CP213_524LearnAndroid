package com.example.randomapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.randomapp.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {

    // เพิ่มอาหาร
    @Insert
    suspend fun insert(meal: MealEntity): Long

    // แก้ไขอาหาร
    @Update
    suspend fun update(meal: MealEntity)

    // ลบอาหาร
    @Delete
    suspend fun delete(meal: MealEntity)

    // ดึงทุกอาหาร
    @Query("SELECT * FROM meals")
    fun getAllMeals(): Flow<List<MealEntity>>

    // ดึงอาหารตาม ID
    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getMealById(id: Int): MealEntity?

    // ดึงอาหารตามหมวดหมู่
    @Query("SELECT * FROM meals WHERE categoryId = :categoryId")
    fun getMealsByCategory(categoryId: Int): Flow<List<MealEntity>>

    // ค้นหาอาหาร
    @Query("SELECT * FROM meals WHERE mealName LIKE '%' || :query || '%'")
    fun searchMeals(query: String): Flow<List<MealEntity>>
}
