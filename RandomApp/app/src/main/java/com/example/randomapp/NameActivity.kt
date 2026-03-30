package com.example.randomapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch

class NameActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    private lateinit var resultText: TextView
    private lateinit var btnRandom: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

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
            seedNamesIfNeeded()
        }

        btnRandom.setOnClickListener {
            lifecycleScope.launch {
                val names = db.nameDao().getAllNames()
                if (names.isNotEmpty()) {
                    resultText.text = names.random().name
                } else {
                    resultText.text = "No names in database"
                }
            }
        }
    }

    private suspend fun seedNamesIfNeeded() {
        if (db.nameDao().getCount() == 0) {
            db.nameDao().insertAll(
                listOf(
                    NameEntity(name = "Andy"),
                    NameEntity(name = "Alice"),
                    NameEntity(name = "Bob"),
                    NameEntity(name = "Charlie"),
                    NameEntity(name = "David"),
                    NameEntity(name = "Emma"),
                    NameEntity(name = "Fah"),
                    NameEntity(name = "Jane")
                )
            )
        }
    }
}
