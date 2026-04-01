package com.example.randomapp

data class MealResponse(
    val meals: List<MealDto>?
)

data class CategoryResponse(
    val categories: List<CategoryDto>?
)

data class MealDto(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String? = null,
    val strArea: String? = null,
    val strInstructions: String? = null
)

data class CategoryDto(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)

data class FoodUiModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val category: String = ""
)