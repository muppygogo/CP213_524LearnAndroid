package com.example.randomapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.randomapp.navigation.Routes
import com.example.randomapp.ui.components.PicklyButton
import com.example.randomapp.ui.components.PicklyTextField
import com.example.randomapp.ui.theme.Primary
import com.example.randomapp.ui.theme.TextSecondary

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pickly",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = Primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Welcome back!",
            fontSize = 16.sp,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(40.dp))

        PicklyTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )

        Spacer(modifier = Modifier.height(16.dp))

        androidx.compose.material3.OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        PicklyButton(
            text = "Login",
            onClick = {
                navController.navigate(Routes.HOME) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {}) {
            Text(
                text = "Don't have an account? Register",
                color = Primary
            )
        }
    }
}
