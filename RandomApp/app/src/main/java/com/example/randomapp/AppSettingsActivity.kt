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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomapp.ui.theme.RandomAppTheme

class AppSettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settings = getSharedPreferences("app_settings", MODE_PRIVATE)

        val savedNotifications = settings.getBoolean("notifications_enabled", true)
        val savedDarkMode = settings.getBoolean("dark_mode_enabled", false)
        val savedThaiLanguage = settings.getBoolean("thai_language_enabled", true)

        setContent {
            RandomAppTheme(
                darkTheme = savedDarkMode,
                dynamicColor = false
            ) {
                AppSettingsScreen(
                    initialNotificationsEnabled = savedNotifications,
                    initialDarkModeEnabled = savedDarkMode,
                    initialThaiLanguageEnabled = savedThaiLanguage,
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
                    onNotificationsChanged = { enabled ->
                        settings.edit().putBoolean("notifications_enabled", enabled).apply()
                        Toast.makeText(
                            this,
                            if (enabled) "เปิดการแจ้งเตือนแล้ว" else "ปิดการแจ้งเตือนแล้ว",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onDarkModeChanged = { enabled ->
                        settings.edit().putBoolean("dark_mode_enabled", enabled).apply()
                        Toast.makeText(
                            this,
                            if (enabled) "เปิดโหมดมืดแล้ว" else "ปิดโหมดมืดแล้ว",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onLanguageChanged = { isThai ->
                        settings.edit().putBoolean("thai_language_enabled", isThai).apply()
                        Toast.makeText(
                            this,
                            if (isThai) "เปลี่ยนเป็นภาษาไทยแล้ว" else "Switched to English",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}

@Composable
fun AppSettingsScreen(
    initialNotificationsEnabled: Boolean,
    initialDarkModeEnabled: Boolean,
    initialThaiLanguageEnabled: Boolean,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onNameClick: () -> Unit,
    onNumberClick: () -> Unit,
    onFoodClick: () -> Unit,
    onProfileClick: () -> Unit,
    onNotificationsChanged: (Boolean) -> Unit,
    onDarkModeChanged: (Boolean) -> Unit,
    onLanguageChanged: (Boolean) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(4) }
    var notifyOn by remember { mutableStateOf(initialNotificationsEnabled) }
    var darkMode by remember { mutableStateOf(initialDarkModeEnabled) }
    var thaiLanguage by remember { mutableStateOf(initialThaiLanguageEnabled) }

    val backgroundColor = if (darkMode) Color(0xFF1E1B22) else Color(0xFFF1DCE4)
    val panelColor = if (darkMode) Color(0xFF2B2530) else Color(0xFFF0D7E0)
    val cardColor = if (darkMode) Color(0xFF362F3D) else Color(0xFFFDFDFD)
    val titleColor = if (darkMode) Color(0xFFF6EAF0) else Color(0xFF3F3039)
    val subtitleColor = if (darkMode) Color(0xFFD2B9C6) else Color(0xFFA68A97)
    val navBgColor = if (darkMode) Color(0xFF2E2833) else Color(0xFFF9F8F8)
    val selectedNavBg = if (darkMode) Color(0xFF4A3141) else Color(0xFFF7E9EE)
    val selectedNavText = Color(0xFFE85C87)
    val unselectedNavText = if (darkMode) Color(0xFFC5AAB8) else Color(0xFFAD8D9C)

    val headerTitle = if (thaiLanguage) "ตั้งค่าแอป" else "App Settings"
    val headerSubtitle = if (thaiLanguage) "ธีม, ภาษา, การแจ้งเตือน" else "Theme, language, notifications"

    val notifyTitle = if (thaiLanguage) "การแจ้งเตือน" else "Notifications"
    val notifySubtitle = if (thaiLanguage) {
        "เปิดหรือปิดการแจ้งเตือนของแอป"
    } else {
        "Enable or disable app notifications"
    }

    val darkTitle = if (thaiLanguage) "โหมดมืด" else "Dark Mode"
    val darkSubtitle = if (thaiLanguage) {
        "เปิดใช้งานธีมสีเข้ม"
    } else {
        "Enable dark theme"
    }

    val languageTitle = if (thaiLanguage) "ภาษาไทย" else "Thai Language"
    val languageSubtitle = if (thaiLanguage) {
        "ใช้ภาษาไทยเป็นภาษาหลัก"
    } else {
        "Use Thai as the primary language"
    }

    val homeLabel = if (thaiLanguage) "หน้าหลัก" else "Home"
    val nameLabel = if (thaiLanguage) "ชื่อ" else "Names"
    val numberLabel = if (thaiLanguage) "ตัวเลข" else "Numbers"
    val foodLabel = if (thaiLanguage) "อาหาร" else "Food"
    val profileLabel = if (thaiLanguage) "โปรไฟล์" else "Profile"
    val backLabel = if (thaiLanguage) "กลับ" else "Back"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
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
                    .background(panelColor)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 18.dp, vertical = 16.dp)
            ) {
                SettingsHeader(
                    title = headerTitle,
                    subtitle = headerSubtitle,
                    backLabel = backLabel,
                    onBackClick = onBackClick
                )

                Spacer(modifier = Modifier.height(18.dp))

                SettingToggleCard(
                    title = notifyTitle,
                    subtitle = notifySubtitle,
                    checked = notifyOn,
                    onCheckedChange = {
                        notifyOn = it
                        onNotificationsChanged(it)
                    },
                    cardColor = cardColor,
                    titleColor = titleColor,
                    subtitleColor = subtitleColor
                )

                Spacer(modifier = Modifier.height(14.dp))

                SettingToggleCard(
                    title = darkTitle,
                    subtitle = darkSubtitle,
                    checked = darkMode,
                    onCheckedChange = {
                        darkMode = it
                        onDarkModeChanged(it)
                    },
                    cardColor = cardColor,
                    titleColor = titleColor,
                    subtitleColor = subtitleColor
                )

                Spacer(modifier = Modifier.height(14.dp))

                SettingToggleCard(
                    title = languageTitle,
                    subtitle = languageSubtitle,
                    checked = thaiLanguage,
                    onCheckedChange = {
                        thaiLanguage = it
                        onLanguageChanged(it)
                    },
                    cardColor = cardColor,
                    titleColor = titleColor,
                    subtitleColor = subtitleColor
                )

                Spacer(modifier = Modifier.height(16.dp))

                InfoPreviewCard(
                    thaiLanguage = thaiLanguage,
                    notificationsEnabled = notifyOn,
                    darkModeEnabled = darkMode,
                    cardColor = cardColor,
                    titleColor = titleColor,
                    subtitleColor = subtitleColor
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            SettingsBottomNav(
                selectedIndex = selectedTab,
                homeLabel = homeLabel,
                nameLabel = nameLabel,
                numberLabel = numberLabel,
                foodLabel = foodLabel,
                profileLabel = profileLabel,
                navBgColor = navBgColor,
                selectedNavBg = selectedNavBg,
                selectedNavText = selectedNavText,
                unselectedNavText = unselectedNavText,
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
private fun SettingsHeader(
    title: String,
    subtitle: String,
    backLabel: String,
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
                        Color(0xFFE1B1EE),
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
                        text = backLabel,
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
private fun SettingToggleCard(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    cardColor: Color,
    titleColor: Color,
    subtitleColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(cardColor)
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 18.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(220.dp)
        ) {
            Text(
                text = title,
                color = titleColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = subtitle,
                color = subtitleColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF5164A8),
                uncheckedThumbColor = Color(0xFF8B8791),
                uncheckedTrackColor = Color(0xFFE2DEE8),
                uncheckedBorderColor = Color(0xFF8B8791)
            )
        )
    }
}

@Composable
private fun InfoPreviewCard(
    thaiLanguage: Boolean,
    notificationsEnabled: Boolean,
    darkModeEnabled: Boolean,
    cardColor: Color,
    titleColor: Color,
    subtitleColor: Color
) {
    val summaryTitle = if (thaiLanguage) "สถานะปัจจุบัน" else "Current Status"
    val notificationText = if (thaiLanguage) {
        if (notificationsEnabled) "การแจ้งเตือน: เปิด" else "การแจ้งเตือน: ปิด"
    } else {
        if (notificationsEnabled) "Notifications: On" else "Notifications: Off"
    }
    val darkModeText = if (thaiLanguage) {
        if (darkModeEnabled) "ธีม: โหมดมืด" else "ธีม: โหมดสว่าง"
    } else {
        if (darkModeEnabled) "Theme: Dark" else "Theme: Light"
    }
    val languageText = if (thaiLanguage) "ภาษา: ไทย" else "Language: English"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(cardColor)
            .padding(18.dp)
    ) {
        Text(
            text = summaryTitle,
            color = titleColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = notificationText,
            color = subtitleColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = darkModeText,
            color = subtitleColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = languageText,
            color = subtitleColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun SettingsBottomNav(
    selectedIndex: Int,
    homeLabel: String,
    nameLabel: String,
    numberLabel: String,
    foodLabel: String,
    profileLabel: String,
    navBgColor: Color,
    selectedNavBg: Color,
    selectedNavText: Color,
    unselectedNavText: Color,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        MiniSettingsNavItem(homeLabel, Icons.Outlined.Home),
        MiniSettingsNavItem(nameLabel, Icons.Outlined.SentimentSatisfied),
        MiniSettingsNavItem(numberLabel, Icons.Outlined.Person),
        MiniSettingsNavItem(foodLabel, Icons.Outlined.Coffee),
        MiniSettingsNavItem(profileLabel, Icons.Outlined.Person)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .clip(RoundedCornerShape(28.dp))
            .background(navBgColor)
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
                    tint = if (selected) selectedNavText else unselectedNavText,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.label,
                    color = if (selected) selectedNavText else unselectedNavText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private data class MiniSettingsNavItem(
    val label: String,
    val icon: ImageVector
)