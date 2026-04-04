package com.example.randomapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.randomapp.data.local.entity.FoodCategory
import com.example.randomapp.data.local.entity.MealEntity
import com.example.randomapp.data.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodViewModel(private val repository: FoodRepository) : ViewModel() {

    // ===== Category State =====

    private val _allCategories = MutableStateFlow<List<FoodCategory>>(emptyList())
    val allCategories: StateFlow<List<FoodCategory>> = _allCategories

    private val _categorySearchResults = MutableStateFlow<List<FoodCategory>>(emptyList())
    val categorySearchResults: StateFlow<List<FoodCategory>> = _categorySearchResults

    private val _currentCategory = MutableStateFlow<FoodCategory?>(null)
    val currentCategory: StateFlow<FoodCategory?> = _currentCategory

    // ===== Meal State =====

    private val _allMeals = MutableStateFlow<List<MealEntity>>(emptyList())
    val allMeals: StateFlow<List<MealEntity>> = _allMeals

    private val _mealSearchResults = MutableStateFlow<List<MealEntity>>(emptyList())
    val mealSearchResults: StateFlow<List<MealEntity>> = _mealSearchResults

    private val _mealsByCategory = MutableStateFlow<List<MealEntity>>(emptyList())
    val mealsByCategory: StateFlow<List<MealEntity>> = _mealsByCategory

    private val _currentMeal = MutableStateFlow<MealEntity?>(null)
    val currentMeal: StateFlow<MealEntity?> = _currentMeal

    // ===== Loading & Error State =====

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    init {
        loadAllCategories()
        loadAllMeals()
    }

    // ===== Category Functions =====

    fun loadAllCategories() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getAllCategories().collect { categories ->
                    _allCategories.value = categories
                }
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการโหลดหมวดหมู่: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun insertCategory(foodCategory: FoodCategory) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.insertCategory(foodCategory)
                _errorMessage.value = "เพิ่มหมวดหมู่สำเร็จ"
                loadAllCategories()
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการเพิ่มหมวดหมู่: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateCategory(foodCategory: FoodCategory) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.updateCategory(foodCategory)
                _errorMessage.value = "แก้ไขหมวดหมู่สำเร็จ"
                loadAllCategories()
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการแก้ไขหมวดหมู่: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteCategory(foodCategory: FoodCategory) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.deleteCategory(foodCategory)
                _errorMessage.value = "ลบหมวดหมู่สำเร็จ"
                loadAllCategories()
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการลบหมวดหมู่: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchCategories(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.searchCategories(query).collect { results ->
                    _categorySearchResults.value = results
                }
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการค้นหาหมวดหมู่: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getCategoryById(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val category = repository.getCategoryById(id)
                _currentCategory.value = category
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการโหลดหมวดหมู่: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ===== Meal Functions =====

    fun loadAllMeals() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getAllMeals().collect { meals ->
                    _allMeals.value = meals
                }
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการโหลดอาหาร: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun insertMeal(meal: MealEntity) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.insertMeal(meal)
                _errorMessage.value = "เพิ่มอาหารสำเร็จ"
                loadAllMeals()
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการเพิ่มอาหาร: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateMeal(meal: MealEntity) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.updateMeal(meal)
                _errorMessage.value = "แก้ไขอาหารสำเร็จ"
                loadAllMeals()
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการแก้ไขอาหาร: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteMeal(meal: MealEntity) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.deleteMeal(meal)
                _errorMessage.value = "ลบอาหารสำเร็จ"
                loadAllMeals()
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการลบอาหาร: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchMeals(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.searchMeals(query).collect { results ->
                    _mealSearchResults.value = results
                }
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการค้นหาอาหาร: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getMealsByCategory(categoryId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getMealsByCategory(categoryId).collect { meals ->
                    _mealsByCategory.value = meals
                }
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการโหลดอาหาร: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getMealById(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val meal = repository.getMealById(id)
                _currentMeal.value = meal
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการโหลดอาหาร: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = ""
    }
}

// Factory สำหรับสร้าง FoodViewModel
class FoodViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FoodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            FoodViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
