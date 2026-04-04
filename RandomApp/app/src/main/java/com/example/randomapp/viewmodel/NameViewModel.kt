package com.example.randomapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.randomapp.data.local.entity.NameEntity
import com.example.randomapp.data.repository.NameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NameViewModel(private val repository: NameRepository) : ViewModel() {

    private val _allNames = MutableStateFlow<List<NameEntity>>(emptyList())
    val allNames: StateFlow<List<NameEntity>> = _allNames

    private val _currentName = MutableStateFlow<NameEntity?>(null)
    val currentName: StateFlow<NameEntity?> = _currentName

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    init {
        loadAllNames()
    }

    fun loadAllNames() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.getAllNames().collect { names ->
                    _allNames.value = names
                }
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการโหลดชื่อ: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun insertName(name: NameEntity) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.insertName(name)
                _errorMessage.value = "เพิ่มชื่อสำเร็จ"
                loadAllNames()
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการเพิ่มชื่อ: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateName(name: NameEntity) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.updateName(name)
                _errorMessage.value = "แก้ไขชื่อสำเร็จ"
                loadAllNames()
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการแก้ไขชื่อ: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteName(name: NameEntity) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.deleteName(name)
                _errorMessage.value = "ลบชื่อสำเร็จ"
                loadAllNames()
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการลบชื่อ: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getNameById(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val name = repository.getNameById(id)
                _currentName.value = name
                _errorMessage.value = ""
            } catch (e: Exception) {
                _errorMessage.value = "ข้อผิดพลาดในการโหลดชื่อ: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = ""
    }
}

class NameViewModelFactory(private val repository: NameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            NameViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
