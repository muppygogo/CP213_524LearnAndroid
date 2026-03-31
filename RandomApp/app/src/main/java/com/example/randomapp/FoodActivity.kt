package com.example.randomapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch

class FoodActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    private lateinit var resultText: TextView
    private lateinit var btnRandom: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        resultText = findViewById(R.id.resultText)
        btnRandom = findViewById(R.id.btnRandom)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "random_app_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        lifecycleScope.launch {
            seedFoodIfNeeded()
        }

        btnRandom.setOnClickListener {
            lifecycleScope.launch {
                val foods = db.foodDao().getAllFoods()
                if (foods.isNotEmpty()) {
                    resultText.text = foods.random().name
                } else {
                    resultText.text = "No food found"
                }
            }
        }
    }

    private suspend fun seedFoodIfNeeded() {
        if (db.foodDao().getCount() == 0) {
            db.foodDao().insertAll(
                listOf(
                    FoodEntity(name = "Pad Thai"),
                    FoodEntity(name = "Krapao"),
                    FoodEntity(name = "Somtam"),
                    FoodEntity(name = "Pizza"),
                    FoodEntity(name = "Burger"),
                    FoodEntity(name = "Sushi")
                )
            )
        }
    }
}