package com.example.randomapp.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.randomapp.ui.components.PicklyButton
import com.example.randomapp.ui.components.PicklyTextField
import com.example.randomapp.ui.theme.Background
import com.example.randomapp.ui.theme.OnPrimary
import com.example.randomapp.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomNumberScreen(navController: NavHostController) {
    var minInput by remember { mutableStateOf("1") }
    var maxInput by remember { mutableStateOf("100") }
    var result by remember { mutableStateOf("?") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Random Number", color = OnPrimary) },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PicklyTextField(
                    value = minInput,
                    onValueChange = { minInput = it },
                    label = "Min",
                    modifier = Modifier.weight(1f)
                )
                PicklyTextField(
                    value = maxInput,
                    onValueChange = { maxInput = it },
                    label = "Max",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedContent(
                targetState = result,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "result"
            ) { targetResult ->
                Text(
                    text = targetResult,
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = Primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            PicklyButton(
                text = "Random!",
                onClick = {
                    val min = minInput.toIntOrNull() ?: 1
                    val max = maxInput.toIntOrNull() ?: 100
                    if (min <= max) {
                        result = (min..max).random().toString()
                    } else {
                        result = "Min must be less than Max"
                    }
                }
            )
        }
    }
}
