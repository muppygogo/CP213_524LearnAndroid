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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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

class NameActivity : ComponentActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "random_app_db"
        ).fallbackToDestructiveMigration().build()

        setContent {
            NameScreen(db)
        }
    }
}

@Composable
fun NameScreen(db: AppDatabase) {

    val scope = rememberCoroutineScope()

    var input by remember { mutableStateOf("") }
    var names by remember { mutableStateOf<List<NameEntity>>(emptyList()) }
    var result by remember { mutableStateOf("ยังไม่ได้สุ่ม") }
    var removeAfter by remember { mutableStateOf(false) }
    var isAnimating by remember { mutableStateOf(false) }

    // โหลดจาก DB
    LaunchedEffect(Unit) {
        names = db.nameDao().getAll()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3DADF))
            .padding(16.dp)
    ) {

        Text("Pick a name", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // RESULT + ANIMATION
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD1AAB1))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text("Result", color = Color.White)

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = result,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = {
                    if (names.isEmpty()) return@Button

                    scope.launch {
                        isAnimating = true

                        repeat(10) {
                            result = names.random().name
                            delay(80)
                        }

                        val final = names.random()
                        result = final.name

                        if (removeAfter) {
                            db.nameDao().deleteById(final.id)
                            names = db.nameDao().getAll()
                        }

                        isAnimating = false
                    }

                }) {
                    Text("Random")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // INPUT
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter names (one per line)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            scope.launch {
                val newNames = input.lines()
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }

                for (n in newNames) {
                    val exist = db.nameDao().checkDuplicate(n)
                    if (exist == null) {
                        db.nameDao().insert(NameEntity(name = n))
                    }
                }

                names = db.nameDao().getAll()
                input = ""
            }
        }) {
            Text("Add")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            RadioButton(
                selected = !removeAfter,
                onClick = { removeAfter = false }
            )
            Text("สุ่มแล้วชื่อยังอยู่")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = removeAfter,
                onClick = { removeAfter = true }
            )
            Text("สุ่มแล้วเอาออก")
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(onClick = {
            scope.launch {
                db.nameDao().deleteAll()
                names = emptyList()
            }
        }) {
            Text("Delete All")
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {
            items(names) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(item.name)

                    IconButton(onClick = {
                        scope.launch {
                            db.nameDao().deleteById(item.id)
                            names = db.nameDao().getAll()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                    }
                }
            }
        }
    }
}