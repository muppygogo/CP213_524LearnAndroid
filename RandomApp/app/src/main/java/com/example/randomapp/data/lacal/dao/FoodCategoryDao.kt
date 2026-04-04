package com.example.randomapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.randomapp.data.local.entity.FoodCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodCategoryDao {

    // เพิ่มหมวดหมู่
    @Insert
    suspend fun insert(foodCategory: FoodCategory): Long

    // แก้ไขหมวดหมู่
    @Update
    suspend fun update(foodCategory: FoodCategory)

    // ลบหมวดหมู่
    @Delete
    suspend fun delete(foodCategory: FoodCategory)

    // ดึงทุกหมวดหมู่
    @Query("SELECT * FROM food_categories")
    fun getAllCategories(): Flow<List<FoodCategory>>

    // ดึงหมวดหมู่ตาม ID
    @Query("SELECT * FROM food_categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): FoodCategory?

    // ค้นหาหมวดหมู่ตามชื่อ
    @Query("SELECT * FROM food_categories WHERE categoryName LIKE '%' || :query || '%'")
    fun searchCategories(query: String): Flow<List<FoodCategory>>
}