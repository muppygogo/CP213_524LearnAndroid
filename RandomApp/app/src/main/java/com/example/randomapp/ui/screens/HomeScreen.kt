package com.example.randomapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.randomapp.navigation.Routes
import com.example.randomapp.ui.theme.Background
import com.example.randomapp.ui.theme.OnPrimary
import com.example.randomapp.ui.theme.Primary
import com.example.randomapp.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pickly",
                        fontWeight = FontWeight.Bold,
                        color = OnPrimary
                    )
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
                .padding(16.dp)
        ) {
            Text(
                text = "What do you want to random?",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HomeCard(
                    title = "Random Name",
                    description = "Random names from your list",
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(Routes.RANDOM_NAME) }
                )
                HomeCard(
                    title = "Random Number",
                    description = "Pick a random number",
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(Routes.RANDOM_NUMBER) }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            HomeCard(
                title = "Random Food (AI)",
                description = "Let AI suggest your next meal",
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(Routes.RANDOM_FOOD) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            HomeCard(
                title = "Profile",
                description = "View your profile",
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(Routes.PROFILE) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeCard(
    description: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = TextSecondary
            )
        }
    }
}
