package com.example.randomapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.randomapp.data.local.entity.RestaurantSearchEntity

@Dao
interface RestaurantSearchDao {

    @Insert
    suspend fun insert(restaurant: RestaurantSearchEntity)

    @Update
    suspend fun update(restaurant: RestaurantSearchEntity)

    @Delete
    suspend fun delete(restaurant: RestaurantSearchEntity)

    @Query("SELECT * FROM restaurant_searches")
    suspend fun getAllRestaurants(): List<RestaurantSearchEntity>

    @Query("SELECT * FROM restaurant_searches WHERE id = :id")
    suspend fun getRestaurantById(id: Int): RestaurantSearchEntity?
}
