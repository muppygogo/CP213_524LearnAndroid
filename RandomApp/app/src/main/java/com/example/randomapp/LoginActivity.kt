package com.example.randomapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = getSharedPreferences("session", MODE_PRIVATE)

        if (session.getBoolean("rememberMe", false) &&
            !session.getString("loggedInEmail", null).isNullOrEmpty()
        ) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "random_app_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        setContent {
            MaterialTheme {
                LoginScreen(
                    onLogin = { email, password, rememberMe ->
                        val cleanEmail = email.trim().lowercase()
                        val cleanPassword = password.trim()

                        lifecycleScope.launch {
                            when {
                                cleanEmail.isEmpty() -> {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Please enter your email",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                cleanPassword.isEmpty() -> {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Please enter your password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                else -> {
                                    val user = db.userDao().login(cleanEmail, cleanPassword)

                                    if (user != null) {
                                        session.edit()
                                            .putString("loggedInEmail", user.email)
                                            .putString("loggedInName", user.fullName)
                                            .putBoolean("rememberMe", rememberMe)
                                            .apply()

                                        Toast.makeText(
                                            this@LoginActivity,
                                            "Login successful",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        startActivity(
                                            Intent(this@LoginActivity, MainActivity::class.java)
                                        )
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "Invalid email or password",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    },
                    onGoToSignup = {
                        startActivity(Intent(this, SignupActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    onLogin: (String, String, Boolean) -> Unit,
    onGoToSignup: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    val bgPink = Color(0xFFF4DADF)
    val linePink = Color(0xFFE6C9D0)
    val accentBlue = Color(0xFF8FB8FF)
    val accentMint = Color(0xFFB9F2D0)
    val softWhite = Color(0xFFFDFBFC)
    val textBlack = Color(0xFF111111)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgPink)
    ) {
        DotDecoration(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 52.dp, end = 10.dp)
                .size(230.dp)
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(78.dp))

            Text(
                text = "Welcome!",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(start = 28.dp),
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.10f),
                        offset = Offset(2f, 4f),
                        blurRadius = 8f
                    )
                )
            )

            Spacer(modifier = Modifier.height(72.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = softWhite,
                    shape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 28.dp, vertical = 38.dp)
                    ) {
                        Spacer(modifier = Modifier.height(28.dp))

                        Text(
                            text = "Login",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = textBlack
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        MinimalUnderlineField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = "Email",
                            lineColor = linePink,
                            keyboardType = KeyboardType.Email
                        )

                        Spacer(modifier = Modifier.height(22.dp))

                        MinimalUnderlinePasswordField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = "Password",
                            lineColor = linePink,
                            showPassword = showPassword,
                            onTogglePassword = { showPassword = !showPassword }
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it }
                            )
                            Text(
                                text = "Remember me",
                                color = textBlack,
                                fontSize = 15.sp
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Don’t have account?",
                                color = textBlack,
                                fontSize = 17.sp
                            )
                            TextButton(onClick = onGoToSignup) {
                                Text(
                                    text = "Sign up",
                                    color = Color(0xFFD7AAB4),
                                    fontSize = 17.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                GradientCircleButton(
                    onClick = { onLogin(email, password, rememberMe) },
                    colors = listOf(accentMint, accentBlue),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-26).dp, y = (-42).dp)
                )
            }
        }
    }
}

@Composable
fun MinimalUnderlineField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    lineColor: Color,
    keyboardType: KeyboardType
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFFADADAD),
                    fontSize = 18.sp
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFFB38C97)
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
                .background(lineColor)
        )
    }
}

@Composable
fun MinimalUnderlinePasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    lineColor: Color,
    showPassword: Boolean,
    onTogglePassword: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFFADADAD),
                    fontSize = 18.sp
                )
            },
            singleLine = true,
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                TextButton(onClick = onTogglePassword) {
                    Text(
                        text = if (showPassword) "Hide" else "Show",
                        color = Color(0xFFD0A7B0)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFFB38C97)
            )
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
                .background(lineColor)
        )
    }
}

@Composable
fun GradientCircleButton(
    onClick: () -> Unit,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(92.dp)
            .clip(CircleShape)
            .background(Brush.linearGradient(colors))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "→",
            color = Color.White,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.offset(y = (-2).dp)
        )
    }
}

@Composable
fun DotDecoration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val centerX = size.width * 0.55f
        val centerY = size.height * 0.45f
        val maxRadius = size.minDimension * 0.42f
        val step = 14f

        var y = 0f
        while (y < size.height) {
            var x = 0f
            while (x < size.width) {
                val dx = x - centerX
                val dy = y - centerY
                val distance = kotlin.math.sqrt(dx * dx + dy * dy)

                if (distance < maxRadius) {
                    val alpha = 1f - (distance / maxRadius)
                    drawCircle(
                        color = Color(0xFF8FE8F6).copy(alpha = alpha * 0.9f),
                        radius = 2.3f + (alpha * 2.2f),
                        center = Offset(x, y)
                    )
                }
                x += step
            }
            y += step
        }
    }
}