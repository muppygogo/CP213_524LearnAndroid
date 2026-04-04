package com.example.randomapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.randomapp.data.local.entity.RestaurantSearchEntity
import com.example.randomapp.data.repository.RestaurantSearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class RestaurantSearchViewModel(private val repository: RestaurantSearchRepository) : ViewModel() {

    private val _allSearches = MutableStateFlow<List<RestaurantSearchEntity>>(emptyList())
    val allSearches: StateFlow<List<RestaurantSearchEntity>> = _allSearches

    private val _searchResults = MutableStateFlow<List<RestaurantSearchEntity>>(emptyList())
    val searchResults: StateFlow<List<RestaurantSearchEntity>> = _searchResults

    private val _aiResponse = MutableStateFlow("")
    val aiResponse: StateFlow<String> = _aiResponse

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    init {
        loadAllSearches()
    }

    fun loadAllSearches() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getAllSearches().collect { searches ->
                    _allSearches.value = searches
                }
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการโหลดประวัติค้นหา: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchRestaurantWithAI(
        query: String,
        location: String = "",
        maxPrice: Double = 0.0
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // เรียก AI
                val aiResult = repository.searchRestaurantWithAI(query, location, maxPrice)
                _aiResponse.value = aiResult

                // บันทึกลงฐานข้อมูล
                val searchEntity = RestaurantSearchEntity(
                    query = query,
                    location = location,
                    maxPrice = maxPrice,
                    resultJson = aiResult
                )
                repository.insertSearch(searchEntity)

                _errorMessage.value = "ค้นหาสำเร็จ"
                loadAllSearches()

            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการค้นหา: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteSearch(search: RestaurantSearchEntity) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.deleteSearch(search)
                _errorMessage.value = "ลบประวัติค้นหาสำเร็จ"
                loadAllSearches()
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการลบ: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchByQuery(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.searchByQuery(query).collect { results ->
                    _searchResults.value = results
                }
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการค้นหา: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = ""
    }
}

class RestaurantSearchViewModelFactory(
    private val repository: RestaurantSearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RestaurantSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            RestaurantSearchViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
