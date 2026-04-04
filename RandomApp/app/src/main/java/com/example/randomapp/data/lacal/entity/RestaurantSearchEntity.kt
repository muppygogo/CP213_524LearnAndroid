package com.example.randomapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_searches")
data class RestaurantSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val restaurantName: String,
    val location: String? = null,
    val searchDate: Long = System.currentTimeMillis()
)
