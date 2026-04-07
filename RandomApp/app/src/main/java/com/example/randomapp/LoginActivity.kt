package com.example.randomapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomapp.ui.theme.RandomAppTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RandomAppTheme {
                LoginScreen(
                    onLoginSuccess = { emailOrPhone ->
                        val session = getSharedPreferences("session", MODE_PRIVATE)
                        session.edit()
                            .putString("loggedInEmail", emailOrPhone)
                            .apply()

                        Toast.makeText(this, "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    },
                    onSignupTabClick = {
                        startActivity(Intent(this, SignupActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onSignupTabClick: () -> Unit
) {
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4DDE5))
    ) {
        TopPinkSection()

        LoginCardSection(
            emailOrPhone = emailOrPhone,
            onEmailOrPhoneChange = {
                emailOrPhone = it
                errorMessage = ""
            },
            password = password,
            onPasswordChange = {
                password = it
                errorMessage = ""
            },
            passwordVisible = passwordVisible,
            onTogglePasswordVisibility = { passwordVisible = !passwordVisible },
            errorMessage = errorMessage,
            onLoginClick = {
                when {
                    emailOrPhone.isBlank() -> {
                        errorMessage = "กรุณากรอกอีเมลหรือเบอร์โทร"
                    }
                    password.isBlank() -> {
                        errorMessage = "กรุณากรอกรหัสผ่าน"
                    }
                    password.length < 4 -> {
                        errorMessage = "รหัสผ่านสั้นเกินไป"
                    }
                    else -> {
                        errorMessage = ""
                        onLoginSuccess(emailOrPhone.trim())
                    }
                }
            },
            onSignupTabClick = onSignupTabClick
        )
    }
}

@Composable
private fun TopPinkSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 36.dp,
                    bottomEnd = 36.dp
                )
            )
            .background(Color(0xFFE9BCCB))
    ) {
        DotPattern(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 70.dp, end = 40.dp)
        )

        GlowBlob(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = 10.dp, y = 10.dp)
                .size(180.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 34.dp, top = 10.dp)
        ) {
            Text(
                text = "Wel-\ncome!",
                style = TextStyle(
                    fontSize = 58.sp,
                    lineHeight = 60.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily.Cursive
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "ยินดีต้อนรับสู่ Pickly",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun LoginCardSection(
    emailOrPhone: String,
    onEmailOrPhoneChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    errorMessage: String,
    onLoginClick: () -> Unit,
    onSignupTabClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 300.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(36.dp))
            .background(Color(0xFFF8F6F7))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 28.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 22.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .offset(y = (-56).dp)
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFFA7D6F8),
                                    Color(0xFFC8A9E9)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Go",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(26.dp))
                    .background(Color(0xFFF0E6EB))
                    .padding(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color(0xFFF8F6F7))
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "เข้าสู่ระบบ",
                        color = Color(0xFFEB5C88),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(22.dp))
                        .clickable { onSignupTabClick() }
                        .padding(vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "สมัครสมาชิก",
                        color = Color(0xFFA78E9C),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(34.dp))

            Text(
                text = "อีเมล / เบอร์โทร",
                color = Color(0xFF6F5664),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            RoundedInputField(
                value = emailOrPhone,
                onValueChange = onEmailOrPhoneChange,
                placeholder = "กรอกอีเมลหรือเบอร์โทร",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "รหัสผ่าน",
                color = Color(0xFF6F5664),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            RoundedPasswordField(
                value = password,
                onValueChange = onPasswordChange,
                placeholder = "รหัสผ่าน",
                passwordVisible = passwordVisible,
                onTogglePasswordVisibility = onTogglePasswordVisibility
            )

            if (errorMessage.isNotBlank()) {
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = errorMessage,
                    color = Color(0xFFE74F7B),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE74F7B),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                contentPadding = PaddingValues()
            ) {
                Text(
                    text = "เข้าสู่ระบบ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ยังไม่มีบัญชี? ",
                    color = Color(0xFFA78E9C),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "สมัครสมาชิก",
                    color = Color(0xFFE74F7B),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.clickable { onSignupTabClick() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun RoundedInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFFB59CA9),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp)),
        shape = RoundedCornerShape(22.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5E8EE),
            unfocusedContainerColor = Color(0xFFF5E8EE),
            disabledContainerColor = Color(0xFFF5E8EE),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = Color(0xFF6F5664),
            unfocusedTextColor = Color(0xFF6F5664),
            cursorColor = Color(0xFFE74F7B)
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
private fun RoundedPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    passwordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFFB59CA9),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp)),
        shape = RoundedCornerShape(22.dp),
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            IconButton(onClick = onTogglePasswordVisibility) {
                Icon(
                    imageVector = if (passwordVisible) {
                        Icons.Default.VisibilityOff
                    } else {
                        Icons.Default.Visibility
                    },
                    contentDescription = "Toggle Password",
                    tint = Color(0xFFB59CA9)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF5E8EE),
            unfocusedContainerColor = Color(0xFFF5E8EE),
            disabledContainerColor = Color(0xFFF5E8EE),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = Color(0xFF6F5664),
            unfocusedTextColor = Color(0xFF6F5664),
            cursorColor = Color(0xFFE74F7B)
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
private fun DotPattern(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            repeat(12) { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    repeat(12) { col ->
                        val show = !(row < 2 && col < 2) && !(row > 9 && col > 9)
                        Box(
                            modifier = Modifier
                                .size(if (show) 5.dp else 0.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFD49FB0))
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GlowBlob(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.18f),
                        Color.Transparent
                    )
                )
            )
    )
}