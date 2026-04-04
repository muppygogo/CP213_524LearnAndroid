package com.example.randomapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("api/json/v1/1/random.php")
    suspend fun getRandomMeal(): MealResponse

    @GET("api/json/v1/1/categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("api/json/v1/1/filter.php")
    suspend fun filterByCategory(
        @Query("c") category: String
    ): MealResponse
}