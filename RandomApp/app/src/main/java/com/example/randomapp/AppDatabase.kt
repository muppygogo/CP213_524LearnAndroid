package com.example.randomapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        FoodEntity::class,
        NameEntity::class,
        NumberEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun foodDao(): FoodDao
    abstract fun nameDao(): NameDao
    abstract fun numberDao(): NumberDao
}