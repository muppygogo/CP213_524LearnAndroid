package com.example.randomapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch

class SignupActivity : ComponentActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "random_app_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        val session = getSharedPreferences("session", MODE_PRIVATE)

        setContent {
            SignupScreen(
                onSignup = { name: String, email: String, password: String, confirmPassword: String ->

                    val cleanName = name.trim()
                    val cleanEmail = email.trim().lowercase()
                    val cleanPassword = password.trim()
                    val cleanConfirmPassword = confirmPassword.trim()

                    lifecycleScope.launch {
                        when {
                            cleanName.isEmpty() -> {
                                Toast.makeText(
                                    this@SignupActivity,
                                    "Please enter your name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            cleanEmail.isEmpty() -> {
                                Toast.makeText(
                                    this@SignupActivity,
                                    "Please enter your email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            !android.util.Patterns.EMAIL_ADDRESS.matcher(cleanEmail).matches() -> {
                                Toast.makeText(
                                    this@SignupActivity,
                                    "Please enter a valid email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            cleanPassword.length < 6 -> {
                                Toast.makeText(
                                    this@SignupActivity,
                                    "Password must be at least 6 characters",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            cleanPassword != cleanConfirmPassword -> {
                                Toast.makeText(
                                    this@SignupActivity,
                                    "Passwords do not match",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                val existingUser = db.userDao().getUserByEmail(cleanEmail)

                                if (existingUser != null) {
                                    Toast.makeText(
                                        this@SignupActivity,
                                        "This email is already registered",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    db.userDao().insertUser(
                                        UserEntity(
                                            fullName = cleanName,
                                            email = cleanEmail,
                                            password = cleanPassword
                                        )
                                    )

                                    session.edit()
                                        .putString("loggedInEmail", cleanEmail)
                                        .apply()

                                    Toast.makeText(
                                        this@SignupActivity,
                                        "Signup successful",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    startActivity(
                                        Intent(this@SignupActivity, MainActivity::class.java)
                                    )
                                    finish()
                                }
                            }
                        }
                    }
                },
                onGoToLogin = {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            )
        }
    }
}

@Composable
fun SignupScreen(
    onSignup: (String, String, String, String) -> Unit,
    onGoToLogin: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFF6D9E4),
                        Color(0xFFF8E8EF),
                        Color(0xFFE4F7FB)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(72.dp))

            Text(
                text = "Join us!",
                fontSize = 42.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 24.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = Color.White.copy(alpha = 0.96f),
                        shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    visualTransformation = if (showPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        TextButton(onClick = { showPassword = !showPassword }) {
                            Text(if (showPassword) "Hide" else "Show")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp),
                    visualTransformation = if (showConfirmPassword) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        TextButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                            Text(if (showConfirmPassword) "Hide" else "Show")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onSignup(name, email, password, confirmPassword)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8FE8F6),
                        contentColor = Color.White
                    )
                ) {
                    Text("Create Account", fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Already have an account?")
                    TextButton(onClick = onGoToLogin) {
                        Text("Login")
                    }
                }
            }
        }
    }
}