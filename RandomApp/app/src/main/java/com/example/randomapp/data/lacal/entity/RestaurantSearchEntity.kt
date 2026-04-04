package com.example.randomapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_searches")
data class RestaurantSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val query: String,  // "อยากกินร้านแถวเยาวราช ราคา 500"
    val location: String = "",
    val maxPrice: Double = 0.0,
    val resultJson: String = "",  // ผลลัพธ์จาก AI
    val userId: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
