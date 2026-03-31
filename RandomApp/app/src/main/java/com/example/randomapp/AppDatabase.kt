package com.example.randomapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        FoodEntity::class,
        NameEntity::class,
        NumberEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun nameDao(): NameDao
    abstract fun numberDao(): NumberDao
    abstract fun userDao(): UserDao
}