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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settings = getSharedPreferences("app_settings", MODE_PRIVATE)
        val darkMode = settings.getBoolean("dark_mode_enabled", false)

        setContent {
            RandomAppTheme(
                darkTheme = darkMode,
                dynamicColor = false
            ) {
                HistoryScreen(
                    isDarkMode = darkMode,
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
                    }
                )
            }
        }
    }
}

private val stringHistorySaver = listSaver<SnapshotStateList<String>, String>(
    save = { it.toList() },
    restore = { restored ->
        mutableStateListOf<String>().apply {
            addAll(restored)
        }
    }
)

@Composable
fun HistoryScreen(
    isDarkMode: Boolean,
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onNameClick: () -> Unit,
    onNumberClick: () -> Unit,
    onFoodClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(4) }

    val historyItems = rememberSaveable(saver = stringHistorySaver) {
        mutableStateListOf(
            "สุ่มรายชื่อ: Andy",
            "สุ่มตัวเลข: 27",
            "สุ่มอาหาร: Pad Thai",
            "สุ่มรายชื่อ: Beam",
            "สุ่มตัวเลข: 91"
        )
    }

    val pageBg = if (isDarkMode) Color(0xFF1E1B22) else Color(0xFFF1DCE4)
    val panelBg = if (isDarkMode) Color(0xFF2B2530) else Color(0xFFF0D7E0)
    val cardBg = if (isDarkMode) Color(0xFF362F3D) else Color(0xFFFDFDFD)
    val titleColor = if (isDarkMode) Color(0xFFF6EAF0) else Color(0xFF44333F)
    val subtitleColor = if (isDarkMode) Color(0xFFD2B9C6) else Color(0xFFA68A97)
    val chipBg = if (isDarkMode) Color(0xFF4A404F) else Color(0xFFF7E9EE)
    val bottomBarBg = if (isDarkMode) Color(0xFF2E2833) else Color(0xFFF9F8F8)
    val selectedNavBg = if (isDarkMode) Color(0xFF4A3141) else Color(0xFFF7E9EE)
    val selectedNavText = Color(0xFFE85C87)
    val normalNavText = if (isDarkMode) Color(0xFFC5AAB8) else Color(0xFFAD8D9C)
    val accent = Color(0xFFE85C87)

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
                HistoryHeader(
                    onBackClick = onBackClick
                )

                Spacer(modifier = Modifier.height(18.dp))

                if (historyItems.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(28.dp))
                            .background(cardBg)
                            .padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.History,
                            contentDescription = "History",
                            tint = accent,
                            modifier = Modifier.size(36.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "ยังไม่มีประวัติ",
                            color = titleColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "เมื่อคุณสุ่มชื่อ ตัวเลข หรืออาหาร รายการจะมาแสดงที่นี่",
                            color = subtitleColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { historyItems.clear() },
                            colors = ButtonDefaults.buttonColors(containerColor = accent),
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Clear",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("ล้างประวัติ", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    historyItems.reversed().forEachIndexed { index, item ->
                        HistoryItemCard(
                            index = index + 1,
                            text = item,
                            cardBg = cardBg,
                            chipBg = chipBg,
                            titleColor = titleColor,
                            subtitleColor = subtitleColor,
                            accent = accent
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            HistoryBottomNav(
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
private fun HistoryHeader(
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
                text = "ประวัติ",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "ดูผลการสุ่มที่ผ่านมา",
                color = Color.White.copy(alpha = 0.92f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun HistoryItemCard(
    index: Int,
    text: String,
    cardBg: Color,
    chipBg: Color,
    titleColor: Color,
    subtitleColor: Color,
    accent: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(cardBg)
            .padding(horizontal = 18.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(chipBg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = index.toString(),
                color = accent,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = text,
                color = titleColor,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "รายการประวัติการสุ่ม",
                color = subtitleColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun HistoryBottomNav(
    selectedIndex: Int,
    bottomBarBg: Color,
    selectedNavBg: Color,
    selectedNavText: Color,
    normalNavText: Color,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        HistoryNavItem("หน้าหลัก", Icons.Outlined.Home),
        HistoryNavItem("ชื่อ", Icons.Outlined.SentimentSatisfied),
        HistoryNavItem("ตัวเลข", Icons.Outlined.Person),
        HistoryNavItem("อาหาร", Icons.Outlined.Coffee),
        HistoryNavItem("โปรไฟล์", Icons.Outlined.Person)
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

private data class HistoryNavItem(
    val label: String,
    val icon: ImageVector
)