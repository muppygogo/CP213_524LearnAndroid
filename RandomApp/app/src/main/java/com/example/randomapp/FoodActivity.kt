package com.example.randomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class FoodActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val foodList = listOf(
                "Pizza",
                "Hamburger",
                "Pad Thai",
                "Fried Rice",
                "Ramen"
            )

            var result by remember { mutableStateOf("Press Random") }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = result)

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = {
                    result = foodList.random()
                }) {
                    Text("Random Food")
                }
            }
        }
    }
}