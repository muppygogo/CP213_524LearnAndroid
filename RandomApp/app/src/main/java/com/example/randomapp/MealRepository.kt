package com.example.randomapp

class MealRepository(
    private val api: MealApiService = MealApi.retrofitService
) {
    suspend fun getApiCategories(): List<String> {
        return api.getCategories()
            .categories
            ?.map { it.strCategory }
            ?.sorted()
            ?: emptyList()
    }

    suspend fun getRandomMeal(): FoodUiModel? {
        val meal = api.getRandomMeal().meals?.firstOrNull() ?: return null
        return FoodUiModel(
            id = meal.idMeal,
            name = meal.strMeal,
            imageUrl = meal.strMealThumb,
            category = meal.strCategory.orEmpty()
        )
    }

    suspend fun getRandomMealByCategory(category: String): FoodUiModel? {
        val meals = api.filterByCategory(category).meals.orEmpty()
        if (meals.isEmpty()) return null

        val random = meals.random()
        return FoodUiModel(
            id = random.idMeal,
            name = random.strMeal,
            imageUrl = random.strMealThumb,
            category = category
        )
    }

    fun getDrinkFallback(): List<FoodUiModel> {
        return listOf(
            FoodUiModel("d1", "Thai Milk Tea 🧋", "", "Drink"),
            FoodUiModel("d2", "Matcha Latte 🍵", "", "Drink"),
            FoodUiModel("d3", "Strawberry Smoothie 🍓", "", "Drink"),
            FoodUiModel("d4", "Lemon Soda 🍋", "", "Drink"),
            FoodUiModel("d5", "Iced Americano ☕", "", "Drink")
        )
    }

    fun mapAppCategoryToApiCategory(appCategory: FoodCategory): String? {
        return when (appCategory) {
            FoodCategory.FOOD -> "Beef"      // เดี๋ยวเราจะไม่ใช้ตรงนี้ตรงๆ
            FoodCategory.DESSERT -> "Dessert"
            FoodCategory.DRINK -> null
        }
    }

    fun getFoodCategoryCandidates(): List<String> {
        return listOf(
            "Beef",
            "Chicken",
            "Pasta",
            "Pork",
            "Seafood",
            "Vegetarian",
            "Breakfast",
            "Lamb",
            "Goat"
        )
    }
}