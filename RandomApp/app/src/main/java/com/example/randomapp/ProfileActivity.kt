package com.example.randomapp

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomapp.ui.theme.RandomAppTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = getSharedPreferences("session", MODE_PRIVATE)
        val settings = getSharedPreferences("app_settings", MODE_PRIVATE)
        val darkMode = settings.getBoolean("dark_mode_enabled", false)

        val savedName = session.getString("userName", null)
        val rawEmail = session.getString("loggedInEmail", "andy@gmail.com") ?: "andy@gmail.com"

        val displayName = savedName ?: rawEmail
            .substringBefore("@")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

        setContent {
            RandomAppTheme(
                darkTheme = darkMode,
                dynamicColor = false
            ) {
                ProfileScreen(
                    isDarkMode = darkMode,
                    userName = displayName,
                    userEmail = rawEmail,
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
                    onPersonalInfoClick = {
                        startActivity(Intent(this, PersonalInfoActivity::class.java))
                    },
                    onSettingsClick = {
                        startActivity(Intent(this, AppSettingsActivity::class.java))
                    },
                    onHistoryClick = {
                        startActivity(Intent(this, HistoryActivity::class.java))
                    },
                    onLogoutClick = {
                        session.edit().clear().apply()

                        val intent = Intent(this, WelcomeActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun ProfileScreen(
    isDarkMode: Boolean,
    userName: String,
    userEmail: String,
    onHomeClick: () -> Unit,
    onNameClick: () -> Unit,
    onNumberClick: () -> Unit,
    onFoodClick: () -> Unit,
    onPersonalInfoClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(4) }

    val pageBg = if (isDarkMode) Color(0xFF1E1B22) else Color(0xFFF1DCE4)
    val panelBg = if (isDarkMode) Color(0xFF2B2530) else Color(0xFFF0D7E0)
    val cardBg = if (isDarkMode) Color(0xFF362F3D) else Color(0xFFFDFDFD)
    val titleColor = if (isDarkMode) Color(0xFFF6EAF0) else Color(0xFF44333F)
    val subtitleColor = if (isDarkMode) Color(0xFFD2B9C6) else Color(0xFFA68A97)
    val bottomBarBg = if (isDarkMode) Color(0xFF2E2833) else Color(0xFFF9F8F8)
    val selectedNavBg = if (isDarkMode) Color(0xFF4A3141) else Color(0xFFF7E9EE)
    val selectedNavText = Color(0xFFE85C87)
    val normalNavText = if (isDarkMode) Color(0xFFC5AAB8) else Color(0xFFAD8D9C)
    val logoutCardBg = if (isDarkMode) Color(0xFF4A2D37) else Color(0xFFFFEEF2)
    val logoutTitle = Color(0xFFE85C87)
    val logoutSubtitle = if (isDarkMode) Color(0xFFF1C7D3) else Color(0xFFB67287)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                ProfileHeader(
                    userName = userName,
                    userEmail = userEmail
                )

                Spacer(modifier = Modifier.height(18.dp))

                ProfileMenuCard(
                    title = "ข้อมูลส่วนตัว",
                    subtitle = "แก้ไขชื่อ อีเมล และรหัสผ่าน",
                    icon = Icons.Outlined.Edit,
                    cardBg = cardBg,
                    titleColor = titleColor,
                    subtitleColor = subtitleColor,
                    onClick = onPersonalInfoClick
                )

                Spacer(modifier = Modifier.height(14.dp))

                ProfileMenuCard(
                    title = "ตั้งค่าแอป",
                    subtitle = "โหมดมืด ภาษา และการแจ้งเตือน",
                    icon = Icons.Outlined.Settings,
                    cardBg = cardBg,
                    titleColor = titleColor,
                    subtitleColor = subtitleColor,
                    onClick = onSettingsClick
                )

                Spacer(modifier = Modifier.height(14.dp))

                ProfileMenuCard(
                    title = "ประวัติ",
                    subtitle = "ดูประวัติการสุ่มล่าสุดของคุณ",
                    icon = Icons.Outlined.History,
                    cardBg = cardBg,
                    titleColor = titleColor,
                    subtitleColor = subtitleColor,
                    onClick = onHistoryClick
                )

                Spacer(modifier = Modifier.height(14.dp))

                ProfileMenuCard(
                    title = "ออกจากระบบ",
                    subtitle = "ล้างข้อมูลการเข้าสู่ระบบและกลับไปหน้าเริ่มต้น",
                    icon = Icons.Outlined.ExitToApp,
                    cardBg = logoutCardBg,
                    titleColor = logoutTitle,
                    subtitleColor = logoutSubtitle,
                    onClick = onLogoutClick
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            ProfileBottomNav(
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
                        4 -> Unit
                    }
                }
            )
        }
    }
}

@Composable
private fun ProfileHeader(
    userName: String,
    userEmail: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
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

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(82.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.22f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName.firstOrNull()?.uppercase() ?: "P",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = userName,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = userEmail,
                color = Color.White.copy(alpha = 0.92f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ProfileMenuCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    cardBg: Color,
    titleColor: Color,
    subtitleColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(cardBg)
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(Color(0xFFF7E9EE)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color(0xFFE85C87),
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = titleColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                color = subtitleColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Text(
            text = "›",
            color = Color(0xFFE85C87),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ProfileBottomNav(
    selectedIndex: Int,
    bottomBarBg: Color,
    selectedNavBg: Color,
    selectedNavText: Color,
    normalNavText: Color,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        ProfileNavItem("หน้าหลัก", Icons.Outlined.Home),
        ProfileNavItem("ชื่อ", Icons.Outlined.SentimentSatisfied),
        ProfileNavItem("ตัวเลข", Icons.Outlined.Person),
        ProfileNavItem("อาหาร", Icons.Outlined.Coffee),
        ProfileNavItem("โปรไฟล์", Icons.Outlined.Person)
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
                    .wrapContentHeight()
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
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

private data class ProfileNavItem(
    val label: String,
    val icon: ImageVector
)