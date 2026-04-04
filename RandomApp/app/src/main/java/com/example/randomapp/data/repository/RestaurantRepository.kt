package com.example.randomapp.data.repository

import com.example.randomapp.data.lacal.entity.AppDatabase
import com.example.randomapp.data.local.entity.RestaurantSearchEntity
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.Flow
import android.content.Context
import com.example.randomapp.BuildConfig

class RestaurantSearchRepository(
    private val database: AppDatabase,
    private val context: Context
) {

    private val searchDao = database.restaurantSearchDao()

    // Gemini AI Model
    private val generativeModel: GenerativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.GEMINI_API_KEY
        )
    }

    // ===== Database Operations =====

    suspend fun insertSearch(search: RestaurantSearchEntity): Long {
        return searchDao.insert(search)
    }

    suspend fun updateSearch(search: RestaurantSearchEntity) {
        searchDao.update(search)
    }

    suspend fun deleteSearch(search: RestaurantSearchEntity) {
        searchDao.delete(search)
    }

    fun getAllSearches(): Flow<List<RestaurantSearchEntity>> {
        return searchDao.getAllSearches()
    }

    suspend fun getSearchById(id: Int): RestaurantSearchEntity? {
        return searchDao.getSearchById(id)
    }

    fun getSearchesByUser(userId: Int): Flow<List<RestaurantSearchEntity>> {
        return searchDao.getSearchesByUser(userId)
    }

    fun searchByQuery(query: String): Flow<List<RestaurantSearchEntity>> {
        return searchDao.searchByQuery(query)
    }

    // ===== Gemini AI =====

    suspend fun searchRestaurantWithAI(
        query: String,
        location: String = "",
        maxPrice: Double = 0.0
    ): String {
        return try {
            val prompt = buildPrompt(query, location, maxPrice)
            val response = generativeModel.generateContent(prompt)
            response.text ?: "ไม่พบร้านอาหารที่ตรงกัน"
        } catch (e: Exception) {
            "เกิดข้อผิดพลาด: ${e.message}"
        }
    }

    private fun buildPrompt(
        query: String,
        location: String,
        maxPrice: Double
    ): String {
        return """
            ช่วยหาร้านอาหารตามข้อมูลนี้:
            - คำค้นหา: $query
            - สถานที่ (ถ้ามี): $location
            - งบประมาณสูงสุด: ${if (maxPrice > 0) "฿$maxPrice" else "ไม่มีกำหนด"}
            
            โปรดให้คำแนะนำร้านอาหารที่เหมาะสม พร้อมชื่อร้าน ที่อยู่ ราคา และเมนูแนะนำ
            ตอบในรูปแบบ JSON ดังนี้:
            {
                "restaurants": [
                    {
                        "name": "ชื่อร้าน",
                        "location": "ที่อยู่",
                        "averagePrice": 250,
                        "recommendedDishes": ["เมนู1", "เมนู2"],
                        "description": "คำอธิบาย"
                    }
                ]
            }
        """.trimIndent()
    }
}
