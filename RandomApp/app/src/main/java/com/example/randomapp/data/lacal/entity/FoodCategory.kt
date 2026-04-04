package com.example.randomapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_categories")
data class FoodCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val categoryName: String,
    val description: String = "",
    val imageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
