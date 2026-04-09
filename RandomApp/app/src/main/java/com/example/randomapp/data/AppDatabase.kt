package com.example.randomapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randomapp.data.FoodDao
import com.example.randomapp.data.FoodEntity
import com.example.randomapp.data.NameDao
import com.example.randomapp.data.NameEntity
import com.example.randomapp.data.NumberDao
import com.example.randomapp.data.NumberEntity
import com.example.randomapp.data.UserDao
import com.example.randomapp.data.UserEntity

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