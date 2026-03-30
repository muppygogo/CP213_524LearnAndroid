package com.example.randomapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NameEntity::class, FoodEntity::class, NumberEntity::class],
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun nameDao(): NameDao
    abstract fun foodDao(): FoodDao
    abstract fun numberDao(): NumberDao
}