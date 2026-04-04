package com.example.randomapp.data.local.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.randomapp.data.local.dao.FoodCategoryDao
import com.example.randomapp.data.local.dao.MealDao
import com.example.randomapp.data.local.dao.NameDao
import com.example.randomapp.data.local.dao.RestaurantSearchDao
import com.example.randomapp.data.local.entity.FoodCategory
import com.example.randomapp.data.local.entity.MealEntity
import com.example.randomapp.data.local.entity.NameEntity
import com.example.randomapp.data.local.entity.RestaurantSearchEntity

@Database(
    entities = [
        FoodCategory::class,
        MealEntity::class,
        NameEntity::class,
        RestaurantSearchEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodCategoryDao(): FoodCategoryDao
    abstract fun mealDao(): MealDao
    abstract fun nameDao(): NameDao
    abstract fun restaurantSearchDao(): RestaurantSearchDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "randomapp_database"
                )
                    .fallbackToDestructiveMigration() // ลบ DB เก่าเมื่ออัปเดต
                    .build()

                instance = db
                db
            }
        }
    }
}
