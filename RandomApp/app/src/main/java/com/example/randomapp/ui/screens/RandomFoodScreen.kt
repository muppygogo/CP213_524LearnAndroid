package com.example.randomapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.randomapp.ui.components.PicklyButton
import com.example.randomapp.ui.theme.Background
import com.example.randomapp.ui.theme.OnPrimary
import com.example.randomapp.ui.theme.Primary
import com.example.randomapp.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomFoodScreen(navController: NavHostController) {
    val foods = listOf(
        "Pad Thai", "Green Curry", "Tom Yum Soup",
        "Mango Sticky Rice", "Massaman Curry", "Som Tum",
        "Khao Man Gai", "Boat Noodles", "Stir-fried Basil"
    )

    var result by remember { mutableStateOf("Ready to pick your meal?") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Random Food (AI)", color = OnPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = OnPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
            )
        },
        containerColor = Background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = result,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Today's recommendation",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(40.dp))

            PicklyButton(
                text = "Suggest my meal!",
                onClick = { result = foods.random() }
            )
        }
    }
}
