package com.example.randomapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FoodUiState(
    val isLoading: Boolean = false,
    val isAnimating: Boolean = false,
    val selectedCategory: FoodCategory = FoodCategory.FOOD,
    val selectedMood: String = "Happy",
    val result: FoodUiModel? = null,
    val error: String? = null
)

class FoodViewModel(
    private val repository: MealRepository = MealRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(FoodUiState())
    val uiState: StateFlow<FoodUiState> = _uiState.asStateFlow()

    fun setCategory(category: FoodCategory) {
        _uiState.value = _uiState.value.copy(selectedCategory = category, error = null)
    }

    fun setMood(mood: String) {
        _uiState.value = _uiState.value.copy(selectedMood = mood)
    }

    fun randomFood() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, isAnimating = true, error = null)

            try {
                repeat(12) {
                    val temp = when (_uiState.value.selectedCategory) {
                        FoodCategory.FOOD -> {
                            val categories = repository.getFoodCategoryCandidates()
                            repository.getRandomMealByCategory(categories.random())
                        }
                        FoodCategory.DESSERT -> repository.getRandomMealByCategory("Dessert")
                        FoodCategory.DRINK -> repository.getDrinkFallback().random()
                    }
                    _uiState.value = _uiState.value.copy(result = temp)
                    delay(80)
                }

                val finalResult = when (_uiState.value.selectedCategory) {
                    FoodCategory.FOOD -> {
                        val categories = repository.getFoodCategoryCandidates()
                        repository.getRandomMealByCategory(categories.random())
                    }
                    FoodCategory.DESSERT -> repository.getRandomMealByCategory("Dessert")
                    FoodCategory.DRINK -> repository.getDrinkFallback().random()
                }

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isAnimating = false,
                    result = finalResult,
                    error = if (finalResult == null) "No food found" else null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isAnimating = false,
                    error = e.message ?: "Something went wrong"
                )
            }
        }
    }

    fun aiSuggestByMood() {
        val mood = _uiState.value.selectedMood.lowercase()

        val suggestion = when (mood) {
            "happy" -> FoodUiModel("m1", "Sushi Party Set 🍣", "", "Mood")
            "sad" -> FoodUiModel("m2", "Warm Chocolate Lava Cake 🍫", "", "Mood")
            "lazy" -> FoodUiModel("m3", "Creamy Carbonara Pasta 🍝", "", "Mood")
            "romantic" -> FoodUiModel("m4", "Strawberry Waffle & Latte 🍓", "", "Mood")
            else -> FoodUiModel("m5", "Chef Surprise 🍽️", "", "Mood")
        }

        _uiState.value = _uiState.value.copy(result = suggestion, error = null)
    }
}

class FoodViewModelFactory(
    private val repository: MealRepository = MealRepository()
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FoodViewModel(repository) as T
    }
}