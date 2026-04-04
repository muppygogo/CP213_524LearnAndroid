package com.example.randomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.randomapp.navigation.PicklyNavGraph
import com.example.randomapp.ui.theme.PicklyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PicklyTheme {
                PicklyApp()
            }
        }
    }
}

@Composable
fun PicklyApp() {
    val navController = rememberNavController()
    PicklyNavGraph(navController = navController)
}
