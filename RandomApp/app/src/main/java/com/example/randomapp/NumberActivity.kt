package com.example.randomapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NumberActivity : ComponentActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "random_app_db"
        ).fallbackToDestructiveMigration().build()

        setContent {
            NumberScreen(db)
        }
    }
}

@Composable
fun NumberScreen(db: AppDatabase) {

    val scope = rememberCoroutineScope()

    var min by remember { mutableStateOf("1") }
    var max by remember { mutableStateOf("100") }
    var amount by remember { mutableStateOf("1") }

    var numbers by remember { mutableStateOf<List<Int>>(emptyList()) }
    var result by remember { mutableStateOf("0") }

    var allowDuplicate by remember { mutableStateOf(true) }
    var removeAfter by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3DADF))
            .padding(16.dp)
    ) {

        Text("Pick a number", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // RESULT
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD1AAB1))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Result", color = Color.White)

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = result,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {
                    val minVal = min.toIntOrNull()
                    val maxVal = max.toIntOrNull()
                    val count = amount.toIntOrNull()

                    if (minVal == null || maxVal == null || count == null) return@Button

                    scope.launch {

                        val pool = if (removeAfter && numbers.isNotEmpty()) {
                            numbers.toMutableList()
                        } else {
                            (minVal..maxVal).toMutableList()
                        }

                        if (!allowDuplicate && count > pool.size) {
                            return@launch
                        }

                        repeat(10) {
                            result = pool.random().toString()
                            delay(60)
                        }

                        val results = mutableListOf<Int>()

                        repeat(count) {
                            val value = if (allowDuplicate) {
                                (minVal..maxVal).random()
                            } else {
                                val r = pool.random()
                                pool.remove(r)
                                r
                            }

                            results.add(value)

                            if (removeAfter) {
                                db.numberDao().deleteByValue(value)
                            }
                        }

                        result = results.joinToString(", ")

                        if (removeAfter) {
                            numbers = db.numberDao().getAll().map { it.value }
                        }
                    }

                }) {
                    Text("Random")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // INPUT
        Row {
            OutlinedTextField(
                value = min,
                onValueChange = { min = it },
                label = { Text("Min") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(10.dp))

            OutlinedTextField(
                value = max,
                onValueChange = { max = it },
                label = { Text("Max") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        // OPTIONS
        Row {
            Checkbox(
                checked = allowDuplicate,
                onCheckedChange = { allowDuplicate = it }
            )
            Text("Allow duplicate")

            Spacer(modifier = Modifier.width(16.dp))

            Checkbox(
                checked = removeAfter,
                onCheckedChange = { removeAfter = it }
            )
            Text("Remove after random")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val minVal = min.toIntOrNull()
            val maxVal = max.toIntOrNull()

            if (minVal == null || maxVal == null) return@Button

            scope.launch {
                val list = (minVal..maxVal).map { NumberEntity(value = it) }
                db.numberDao().deleteAll()
                db.numberDao().insertAll(list)
                numbers = list.map { it.value }
            }
        }) {
            Text("Generate pool")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            scope.launch {
                db.numberDao().deleteAll()
                numbers = emptyList()
            }
        }) {
            Text("Clear all")
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(numbers) {
                Text(
                    text = it.toString(),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}