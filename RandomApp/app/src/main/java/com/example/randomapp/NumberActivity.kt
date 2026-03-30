package com.example.randomapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch

class NumberActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    private lateinit var resultText: TextView
    private lateinit var btnRandom: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number)

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
            seedNumbersIfNeeded()
        }

        btnRandom.setOnClickListener {
            lifecycleScope.launch {
                val numbers = db.numberDao().getAllNumbers()
                if (numbers.isNotEmpty()) {
                    resultText.text = numbers.random().value.toString()
                } else {
                    resultText.text = "No numbers in database"
                }
            }
        }
    }

    private suspend fun seedNumbersIfNeeded() {
        if (db.numberDao().getCount() == 0) {
            val list = (1..100).map { NumberEntity(value = it) }
            db.numberDao().insertAll(list)
        }
    }
}