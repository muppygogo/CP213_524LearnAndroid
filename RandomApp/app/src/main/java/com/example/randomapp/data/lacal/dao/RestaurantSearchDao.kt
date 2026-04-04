package com.example.randomapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.randomapp.data.local.entity.RestaurantSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantSearchDao {

    @Insert
    suspend fun insert(search: RestaurantSearchEntity): Long

    @Update
    suspend fun update(search: RestaurantSearchEntity)

    @Delete
    suspend fun delete(search: RestaurantSearchEntity)

    @Query("SELECT * FROM restaurant_searches")
    fun getAllSearches(): Flow<List<RestaurantSearchEntity>>

    @Query("SELECT * FROM restaurant_searches WHERE id = :id")
    suspend fun getSearchById(id: Int): RestaurantSearchEntity?

    @Query("SELECT * FROM restaurant_searches WHERE userId = :userId ORDER BY createdAt DESC")
    fun getSearchesByUser(userId: Int): Flow<List<RestaurantSearchEntity>>

    @Query("SELECT * FROM restaurant_searches WHERE query LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchByQuery(query: String): Flow<List<RestaurantSearchEntity>>
}
