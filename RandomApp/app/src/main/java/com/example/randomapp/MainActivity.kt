package com.example.randomapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Random App")

                Spacer(modifier = Modifier.height(30.dp))

                Button(onClick = {
                    val intent = Intent(this@MainActivity, FoodActivity::class.java)
                    startActivity(intent)
                }) {
                    Text("Random Food")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val intent = Intent(this@MainActivity, NameActivity::class.java)
                    startActivity(intent)
                }) {
                    Text("Random Name")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val intent = Intent(this@MainActivity, NumberActivity::class.java)
                    startActivity(intent)
                }) {
                    Text("Random Number")
                }

            }

        }
    }
}