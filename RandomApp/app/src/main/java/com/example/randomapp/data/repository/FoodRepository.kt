package com.example.randomapp.data.repository

import com.example.randomapp.data.lacal.entity.AppDatabase
import com.example.randomapp.data.local.entity.FoodCategory
import com.example.randomapp.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

class FoodRepository(private val database: AppDatabase) {

    private val foodCategoryDao = database.foodCategoryDao()
    private val mealDao = database.mealDao()

    // ===== FoodCategory =====

    suspend fun insertCategory(foodCategory: FoodCategory): Long {
        return foodCategoryDao.insert(foodCategory)
    }

    suspend fun updateCategory(foodCategory: FoodCategory) {
        foodCategoryDao.update(foodCategory)
    }

    suspend fun deleteCategory(foodCategory: FoodCategory) {
        foodCategoryDao.delete(foodCategory)
    }

    fun getAllCategories(): Flow<List<FoodCategory>> {
        return foodCategoryDao.getAllCategories()
    }

    suspend fun getCategoryById(id: Int): FoodCategory? {
        return foodCategoryDao.getCategoryById(id)
    }

    fun searchCategories(query: String): Flow<List<FoodCategory>> {
        return foodCategoryDao.searchCategories(query)
    }

    // ===== Meal =====

    suspend fun insertMeal(meal: MealEntity): Long {
        return mealDao.insert(meal)
    }

    suspend fun updateMeal(meal: MealEntity) {
        mealDao.update(meal)
    }

    suspend fun deleteMeal(meal: MealEntity) {
        mealDao.delete(meal)
    }

    fun getAllMeals(): Flow<List<MealEntity>> {
        return mealDao.getAllMeals()
    }

    suspend fun getMealById(id: Int): MealEntity? {
        return mealDao.getMealById(id)
    }

    fun getMealsByCategory(categoryId: Int): Flow<List<MealEntity>> {
        return mealDao.getMealsByCategory(categoryId)
    }

    fun searchMeals(query: String): Flow<List<MealEntity>> {
        return mealDao.searchMeals(query)
    }
}
