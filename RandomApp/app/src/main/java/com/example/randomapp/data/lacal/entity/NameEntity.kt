package com.example.randomapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "names")
data class NameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val userId: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
