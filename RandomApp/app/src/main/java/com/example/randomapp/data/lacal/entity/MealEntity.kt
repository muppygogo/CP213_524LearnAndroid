package com.example.randomapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "meals",
    foreignKeys = [
        ForeignKey(
            entity = FoodCategory::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val mealName: String,
    val categoryId: Int,
    val description: String = "",
    val imageUrl: String = "",
    val price: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)
