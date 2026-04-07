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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SentimentSatisfied
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = getSharedPreferences("session", MODE_PRIVATE)
        val settings = getSharedPreferences("app_settings", MODE_PRIVATE)
        val darkMode = settings.getBoolean("dark_mode_enabled", false)

        val savedName = session.getString("userName", null)
        val rawEmail = session.getString("loggedInEmail", "แพรวพราว") ?: "แพรวพราว"

        val displayName = savedName ?: rawEmail
            .substringBefore("@")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

        setContent {
            RandomAppTheme(
                darkTheme = darkMode,
                dynamicColor = false
            ) {
                MainScreen(
                    isDarkMode = darkMode,
                    username = displayName,
                    onNameClick = {
                        startActivity(Intent(this, NameActivity::class.java))
                    },
                    onNumberClick = {
                        startActivity(Intent(this, NumberActivity::class.java))
                    },
                    onFoodClick = {
                        startActivity(Intent(this, FoodActivity::class.java))
                    },
                    onNavClick = { index ->
                        when (index) {
                            0 -> Unit
                            1 -> startActivity(Intent(this, NameActivity::class.java))
                            2 -> startActivity(Intent(this, NumberActivity::class.java))
                            3 -> startActivity(Intent(this, FoodActivity::class.java))
                            4 -> startActivity(Intent(this, ProfileActivity::class.java))
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    isDarkMode: Boolean,
    username: String,
    onNameClick: () -> Unit,
    onNumberClick: () -> Unit,
    onFoodClick: () -> Unit,
    onNavClick: (Int) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val pageBg = if (isDarkMode) Color(0xFF1E1B22) else Color(0xFFF1DCE4)
    val panelBgTop = if (isDarkMode) Color(0xFF2B2530) else Color(0xFFF9EFF3)
    val panelBgBottom = if (isDarkMode) Color(0xFF362F3D) else Color(0xFFF0D8E0)
    val titleText = if (isDarkMode) Color(0xFFF6EAF0) else Color(0xFF44333F)
    val subText = if (isDarkMode) Color(0xFFD2B9C6) else Color(0xFFB893A2)
    val bottomBarBg = if (isDarkMode) Color(0xFF2E2833) else Color(0xFFF9F8F8)
    val selectedBg = if (isDarkMode) Color(0xFF4A3141) else Color(0xFFF7E9EE)
    val normalNavText = if (isDarkMode) Color(0xFFC5AAB8) else Color(0xFFAD8D9C)
    val activeNavText = Color(0xFFE85C87)

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
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(34.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                panelBgTop,
                                panelBgBottom
                            )
                        )
                    )
                    .padding(horizontal = 18.dp, vertical = 22.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    HeaderSection(
                        username = username,
                        titleColor = titleText,
                        subtitleColor = subText
                    )

                    Spacer(modifier = Modifier.height(34.dp))

                    Text(
                        text = "เลือกฟีเจอร์",
                        color = subText,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    FeatureCard(
                        title = "สุ่มรายชื่อ",
                        subtitle = "เพิ่มชื่อแล้วให้ Pickly เลือกให้",
                        cardBrush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFB893A9),
                                Color(0xFFAE87A3)
                            )
                        ),
                        circleColor = Color(0xFFD2BDD0),
                        titleColor = Color.White,
                        subtitleColor = Color.White.copy(alpha = 0.92f),
                        iconType = FeatureIconType.NAME,
                        onClick = onNameClick
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    FeatureCard(
                        title = "สุ่มตัวเลข",
                        subtitle = "กำหนดช่วงแล้วสุ่มได้เลย",
                        cardBrush = Brush.linearGradient(
                            colors = if (isDarkMode) {
                                listOf(Color(0xFF3A3342), Color(0xFF4A404F))
                            } else {
                                listOf(Color(0xFFFDFDFD), Color(0xFFF9F8F9))
                            }
                        ),
                        circleColor = Color(0xFFD87AA7),
                        titleColor = if (isDarkMode) Color.White else Color(0xFF6E5663),
                        subtitleColor = if (isDarkMode) Color(0xFFD2B9C6) else Color(0xFFA98899),
                        iconType = FeatureIconType.NUMBER,
                        onClick = onNumberClick
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    FeatureCard(
                        title = "สุ่มอาหาร",
                        subtitle = "AI ช่วยเลือกเมนูให้คุณ",
                        cardBrush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFA9CBE4),
                                Color(0xFFC5BCE9)
                            )
                        ),
                        circleColor = Color(0xFFD4CFEA),
                        titleColor = Color.White,
                        subtitleColor = Color.White.copy(alpha = 0.92f),
                        iconType = FeatureIconType.FOOD,
                        onClick = onFoodClick
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            BottomNavBar(
                selectedIndex = selectedTab,
                barBg = bottomBarBg,
                selectedBg = selectedBg,
                activeText = activeNavText,
                normalText = normalNavText,
                onTabSelected = {
                    selectedTab = it
                    onNavClick(it)
                }
            )
        }
    }
}

@Composable
private fun HeaderSection(
    username: String,
    titleColor: Color,
    subtitleColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "สวัสดี,",
                color = subtitleColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = username,
                color = titleColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.Black
            )
        }

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFE59ABB),
                            Color(0xFFC9A6E2)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = username.firstOrNull()?.uppercase() ?: "P",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

private enum class FeatureIconType {
    NAME, NUMBER, FOOD
}

@Composable
private fun FeatureCard(
    title: String,
    subtitle: String,
    cardBrush: Brush,
    circleColor: Color,
    titleColor: Color,
    subtitleColor: Color,
    iconType: FeatureIconType,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(cardBrush)
            .clickable { onClick() }
            .padding(start = 24.dp, end = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = titleColor,
                fontSize = 25.sp,
                fontWeight = FontWeight.Black,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subtitle,
                color = subtitleColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 16.sp,
                maxLines = 2
            )
        }

        Box(
            modifier = Modifier
                .size(92.dp)
                .clip(CircleShape)
                .background(circleColor),
            contentAlignment = Alignment.Center
        ) {
            when (iconType) {
                FeatureIconType.NAME -> {
                    Icon(
                        imageVector = Icons.Outlined.SentimentSatisfied,
                        contentDescription = title,
                        tint = Color.White,
                        modifier = Modifier.size(42.dp)
                    )
                }

                FeatureIconType.NUMBER -> {
                    Text(
                        text = "9",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                FeatureIconType.FOOD -> {
                    Icon(
                        imageVector = Icons.Outlined.Coffee,
                        contentDescription = title,
                        tint = Color.White,
                        modifier = Modifier.size(42.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavBar(
    selectedIndex: Int,
    barBg: Color,
    selectedBg: Color,
    activeText: Color,
    normalText: Color,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        NavItem("หน้าหลัก", Icons.Outlined.Home),
        NavItem("ชื่อ", Icons.Outlined.SentimentSatisfied),
        NavItem("ตัวเลข", Icons.Outlined.Person),
        NavItem("อาหาร", Icons.Outlined.Coffee),
        NavItem("โปรไฟล์", Icons.Outlined.Person)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .clip(RoundedCornerShape(28.dp))
            .background(barBg)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            val selected = selectedIndex == index

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(22.dp))
                    .background(if (selected) selectedBg else Color.Transparent)
                    .clickable { onTabSelected(index) }
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = if (selected) activeText else normalText,
                    modifier = Modifier.size(30.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = item.label,
                    color = if (selected) activeText else normalText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

private data class NavItem(
    val label: String,
    val icon: ImageVector
)