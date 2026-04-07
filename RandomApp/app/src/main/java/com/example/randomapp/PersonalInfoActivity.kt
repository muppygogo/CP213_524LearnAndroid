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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.randomapp.ui.theme.RandomAppTheme
import kotlinx.coroutines.launch

class PersonalInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = getSharedPreferences("session", MODE_PRIVATE)
        val settings = getSharedPreferences("app_settings", MODE_PRIVATE)
        val darkMode = settings.getBoolean("dark_mode_enabled", false)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "random_app_db"
        ).fallbackToDestructiveMigration().build()

        val currentEmail = session.getString("loggedInEmail", "andy@gmail.com") ?: "andy@gmail.com"
        val savedName = session.getString("userName", null)
        val displayName = savedName ?: currentEmail.substringBefore("@")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

        setContent {
            RandomAppTheme(
                darkTheme = darkMode,
                dynamicColor = false
            ) {
                PersonalInfoScreen(
                    isDarkMode = darkMode,
                    initialName = displayName,
                    initialEmail = currentEmail,
                    onBackClick = { finish() },
                    onHomeClick = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    },
                    onNameClick = {
                        startActivity(Intent(this, NameActivity::class.java))
                        finish()
                    },
                    onNumberClick = {
                        startActivity(Intent(this, NumberActivity::class.java))
                        finish()
                    },
                    onFoodClick = {
                        startActivity(Intent(this, FoodActivity::class.java))
                        finish()
                    },
                    onProfileClick = {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    },
                    onSaveClick = { newName, newEmail, newPassword ->
                        val finalName = newName.trim()
                        val finalEmail = newEmail.trim()
                        val finalPassword = newPassword.trim()

                        if (finalName.isBlank()) {
                            Toast.makeText(this, "กรุณากรอกชื่อ", Toast.LENGTH_SHORT).show()
                            return@PersonalInfoScreen false
                        }

                        if (finalEmail.isBlank()) {
                            Toast.makeText(this, "กรุณากรอกอีเมล", Toast.LENGTH_SHORT).show()
                            return@PersonalInfoScreen false
                        }

                        lifecycleScope.launch {
                            val userDao = db.userDao()
                            val oldUser = userDao.getUserByEmail(currentEmail)

                            if (oldUser == null) {
                                Toast.makeText(
                                    this@PersonalInfoActivity,
                                    "ไม่พบผู้ใช้ในระบบ",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@launch
                            }

                            val emailOwner = userDao.getUserByEmail(finalEmail)
                            if (finalEmail != currentEmail && emailOwner != null) {
                                Toast.makeText(
                                    this@PersonalInfoActivity,
                                    "อีเมลนี้ถูกใช้งานแล้ว",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@launch
                            }

                            val updatedUser = oldUser.copy(
                                name = finalName,
                                email = finalEmail,
                                password = if (finalPassword.isNotBlank()) finalPassword else oldUser.password
                            )

                            userDao.updateUser(updatedUser)

                            session.edit()
                                .putString("userName", finalName)
                                .putString("loggedInEmail", finalEmail)
                                .apply()

                            Toast.makeText(
                                this@PersonalInfoActivity,
                                "บันทึกข้อมูลแล้ว",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        true
                    }
                )
            }
        }
    }
}

@Composable
fun PersonalInfoScreen(
    isDarkMode: Boolean,
    initialName: String,
    initialEmail: String,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onNameClick: () -> Unit,
    onNumberClick: () -> Unit,
    onFoodClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSaveClick: (String, String, String) -> Boolean
) {
    var selectedTab by remember { mutableIntStateOf(4) }
    var name by remember { mutableStateOf(initialName) }
    var email by remember { mutableStateOf(initialEmail) }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(initialName, initialEmail) {
        name = initialName
        email = initialEmail
    }

    val pageBg = if (isDarkMode) Color(0xFF1E1B22) else Color(0xFFF1DCE4)
    val panelBg = if (isDarkMode) Color(0xFF2B2530) else Color(0xFFF0D7E0)
    val cardBg = if (isDarkMode) Color(0xFF362F3D) else Color(0xFFFDFDFD)
    val inputBg = if (isDarkMode) Color(0xFF463D4D) else Color(0xFFF7EDF1)
    val titleColor = if (isDarkMode) Color(0xFFF6EAF0) else Color(0xFF4B3945)
    val subtitleColor = if (isDarkMode) Color(0xFFD2B9C6) else Color(0xFFB79AA8)
    val bottomBarBg = if (isDarkMode) Color(0xFF2E2833) else Color(0xFFF9F8F8)
    val selectedNavBg = if (isDarkMode) Color(0xFF4A3141) else Color(0xFFF7E9EE)
    val selectedNavText = Color(0xFFE85C87)
    val normalNavText = if (isDarkMode) Color(0xFFC5AAB8) else Color(0xFFAD8D9C)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(34.dp))
                    .background(panelBg)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 18.dp, vertical = 16.dp)
            ) {
                TopSimpleHeader(
                    title = "ข้อมูลส่วนตัว",
                    subtitle = "แก้ไขชื่อ, อีเมล, รหัสผ่าน",
                    onBackClick = onBackClick
                )

                Spacer(modifier = Modifier.height(18.dp))

                InfoInputCard(
                    label = "ชื่อ",
                    value = name,
                    onValueChange = { name = it },
                    hint = "กรอกชื่อ",
                    isPassword = false,
                    cardBg = cardBg,
                    inputBg = inputBg,
                    titleColor = titleColor,
                    subtitleColor = subtitleColor
                )

                Spacer(modifier = Modifier.height(14.dp))

                InfoInputCard(
                    label = "อีเมล",
                    value = email,
                    onValueChange = { email = it },
                    hint = "กรอกอีเมล",
                    isPassword = false,
                    cardBg = cardBg,
                    inputBg = inputBg,
                    titleColor = titleColor,
                    subtitleColor = subtitleColor
                )

                Spacer(modifier = Modifier.height(14.dp))

                InfoInputCard(
                    label = "รหัสผ่านใหม่",
                    value = password,
                    onValueChange = { password = it },
                    hint = "กรอกรหัสผ่านใหม่",
                    isPassword = true,
                    cardBg = cardBg,
                    inputBg = inputBg,
                    titleColor = titleColor,
                    subtitleColor = subtitleColor
                )

                Spacer(modifier = Modifier.height(22.dp))

                ActionButton(
                    text = "บันทึกข้อมูล",
                    onClick = {
                        val success = onSaveClick(name, email, password)
                        if (success) {
                            password = ""
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            ProfileLikeBottomNav(
                selectedIndex = selectedTab,
                bottomBarBg = bottomBarBg,
                selectedNavBg = selectedNavBg,
                selectedNavText = selectedNavText,
                normalNavText = normalNavText,
                onTabSelected = {
                    selectedTab = it
                    when (it) {
                        0 -> onHomeClick()
                        1 -> onNameClick()
                        2 -> onNumberClick()
                        3 -> onFoodClick()
                        4 -> onProfileClick()
                    }
                }
            )
        }
    }
}

@Composable
private fun TopSimpleHeader(
    title: String,
    subtitle: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(34.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFE68EA9),
                        Color(0xFFC9B4E5)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .size(190.dp)
                .align(Alignment.TopEnd)
                .background(Color.White.copy(alpha = 0.08f), CircleShape)
        )

        Column {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White.copy(alpha = 0.18f))
                    .clickable { onBackClick() }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "กลับ",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.92f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun InfoInputCard(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isPassword: Boolean,
    cardBg: Color,
    inputBg: Color,
    titleColor: Color,
    subtitleColor: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(cardBg)
            .padding(18.dp)
    ) {
        Text(
            text = label,
            color = titleColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(inputBg)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            if (value.isEmpty()) {
                Text(
                    text = hint,
                    color = subtitleColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    color = titleColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                ),
                visualTransformation = if (isPassword) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFE85B87),
                        Color(0xFFDB72A3)
                    )
                )
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun ProfileLikeBottomNav(
    selectedIndex: Int,
    bottomBarBg: Color,
    selectedNavBg: Color,
    selectedNavText: Color,
    normalNavText: Color,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        MiniNavItem("หน้าหลัก", Icons.Outlined.Home),
        MiniNavItem("ชื่อ", Icons.Outlined.SentimentSatisfied),
        MiniNavItem("ตัวเลข", Icons.Outlined.Person),
        MiniNavItem("อาหาร", Icons.Outlined.Coffee),
        MiniNavItem("โปรไฟล์", Icons.Outlined.Person)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .clip(RoundedCornerShape(28.dp))
            .background(bottomBarBg)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            val selected = selectedIndex == index

            Column(
                modifier = Modifier
                    .width(64.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(if (selected) selectedNavBg else Color.Transparent)
                    .clickable { onTabSelected(index) }
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = if (selected) selectedNavText else normalNavText,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.label,
                    color = if (selected) selectedNavText else normalNavText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private data class MiniNavItem(
    val label: String,
    val icon: ImageVector
)