package com.example.randomapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.randomapp.navigation.Routes
import com.example.randomapp.ui.theme.OnPrimary
import com.example.randomapp.ui.theme.Primary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000)
        navController.navigate(Routes.LOGIN) {
            popUpTo(Routes.SPLASH) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Pickly",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = OnPrimary,
            modifier = Modifier.alpha(alphaAnim)
        )
    }
}
