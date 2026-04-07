package com.example.randomapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomapp.ui.theme.RandomAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NumberActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settings = getSharedPreferences("app_settings", MODE_PRIVATE)
        val darkMode = settings.getBoolean("dark_mode_enabled", false)

        setContent {
            RandomAppTheme(
                darkTheme = darkMode,
                dynamicColor = false
            ) {
                NumberScreen(
                    isDarkMode = darkMode,
                    onBack = { finish() },
                    onHomeClick = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    },
                    onNameClick = {
                        startActivity(Intent(this, NameActivity::class.java))
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

private val intListSaver = listSaver<SnapshotStateList<Int>, Int>(
    save = { it.toList() },
    restore = { restored ->
        mutableStateListOf<Int>().apply {
            addAll(restored)
        }
    }
)

@Composable
fun NumberScreen(
    isDarkMode: Boolean,
    onBack: () -> Unit,
    onHomeClick: () -> Unit,
    onNameClick: () -> Unit,
    onFoodClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var minNumber by rememberSaveable { mutableIntStateOf(1) }
    var maxNumber by rememberSaveable { mutableIntStateOf(100) }

    var result by rememberSaveable { mutableStateOf("") }
    var displayNumber by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    var removeAfterPick by rememberSaveable { mutableStateOf(true) }

    val history = rememberSaveable(saver = intListSaver) {
        mutableStateListOf<Int>()
    }

    val removedNumbers = rememberSaveable(saver = intListSaver) {
        mutableStateListOf<Int>()
    }

    val scope = rememberCoroutineScope()
    val resultScale = remember { Animatable(1f) }
    val resultAlpha = remember { Animatable(1f) }
    var isAnimating by remember { mutableStateOf(false) }

    val pageBg = if (isDarkMode) Color(0xFF1E1B22) else Color(0xFFF2DFE6)
    val cardBg = if (isDarkMode) Color(0xFF362F3D) else Color(0xFFF9F8F8)
    val chipBg = if (isDarkMode) Color(0xFF4A404F) else Color(0xFFF8F6F7)
    val numberBoxBg = if (isDarkMode) Color(0xFF463D4D) else Color(0xFFF5E8EE)
    val textPrimary = if (isDarkMode) Color(0xFFF6EAF0) else Color(0xFF6E5663)
    val textSecondary = if (isDarkMode) Color(0xFFD2B9C6) else Color(0xFFB59CA9)
    val normalButtonBg = if (isDarkMode) Color(0xFF4A404F) else Color(0xFFF5E8EE)
    val normalButtonText = if (isDarkMode) Color(0xFFD8C0CC) else Color(0xFF8E6F80)
    val bottomBarBg = if (isDarkMode) Color(0xFF2E2833) else Color(0xFFF8EFF3)
    val bottomBarText = if (isDarkMode) Color(0xFFC5AAB8) else Color(0xFF8E6F80)
    val accent = Color(0xFFE14D77)

    fun adjustMin(delta: Int) {
        val newValue = (minNumber + delta).coerceAtLeast(0)
        minNumber = if (newValue <= maxNumber) newValue else maxNumber
        removedNumbers.removeAll { it < minNumber || it > maxNumber }
    }

    fun adjustMax(delta: Int) {
        val newValue = (maxNumber + delta).coerceAtLeast(minNumber)
        maxNumber = newValue
        removedNumbers.removeAll { it < minNumber || it > maxNumber }
    }

    fun runRandomNumber() {
        if (minNumber > maxNumber) {
            errorMessage = "ค่าต่ำสุดต้องไม่มากกว่าค่าสูงสุด"
            return
        }

        val availableNumbers = if (removeAfterPick) {
            (minNumber..maxNumber).filter { it !in removedNumbers }
        } else {
            (minNumber..maxNumber).toList()
        }

        if (availableNumbers.isEmpty()) {
            errorMessage = "ไม่มีเลขให้สุ่มแล้วในช่วงนี้"
            return
        }

        scope.launch {
            isAnimating = true
            errorMessage = ""
            result = ""

            repeat(20) {
                displayNumber = availableNumbers.random().toString()
                delay(55)
            }

            repeat(8) {
                displayNumber = availableNumbers.random().toString()
                delay(110)
            }

            val finalNumber = availableNumbers.random()
            displayNumber = finalNumber.toString()
            result = finalNumber.toString()
            isAnimating = false

            history.add(finalNumber)

            if (removeAfterPick) {
                removedNumbers.add(finalNumber)
            }

            resultScale.snapTo(0.9f)
            resultAlpha.snapTo(0.6f)

            launch {
                resultScale.animateTo(
                    targetValue = 1.08f,
                    animationSpec = tween(180)
                )
                resultScale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(220, easing = FastOutSlowInEasing)
                )
            }

            launch {
                resultAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(240)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBg)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFFEA6C90),
                                Color(0xFFE27D9D)
                            )
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(220.dp)
                        .align(Alignment.TopEnd)
                        .background(
                            Color.White.copy(alpha = 0.10f),
                            shape = CircleShape
                        )
                )

                Column {
                    Box(
                        modifier = Modifier
                            .background(
                                Color.White.copy(alpha = 0.20f),
                                shape = RoundedCornerShape(18.dp)
                            )
                            .clickable { onBack() }
                            .padding(horizontal = 18.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "← กลับ",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "สุ่มตัวเลข",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "กำหนดช่วงตัวเลขที่ต้องการ",
                        color = Color.White.copy(alpha = 0.94f),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "• กำหนดช่วง",
                        color = textPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        NumberValueBox(
                            value = minNumber,
                            onIncrease = { adjustMin(1) },
                            onDecrease = { adjustMin(-1) },
                            modifier = Modifier.weight(1f),
                            boxBg = numberBoxBg,
                            textColor = textPrimary
                        )

                        Text(
                            text = "—",
                            color = textSecondary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(horizontal = 14.dp)
                        )

                        NumberValueBox(
                            value = maxNumber,
                            onIncrease = { adjustMax(1) },
                            onDecrease = { adjustMax(-1) },
                            modifier = Modifier.weight(1f),
                            boxBg = numberBoxBg,
                            textColor = textPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row {
                        Button(
                            onClick = { removeAfterPick = true },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (removeAfterPick) accent else normalButtonBg,
                                contentColor = if (removeAfterPick) Color.White else normalButtonText
                            )
                        ) {
                            Text("สุ่มแล้วลบออก")
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(
                            onClick = { removeAfterPick = false },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (!removeAfterPick) accent else normalButtonBg,
                                contentColor = if (!removeAfterPick) Color.White else normalButtonText
                            )
                        ) {
                            Text("สุ่มแล้วคงอยู่")
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = { runRandomNumber() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accent,
                            contentColor = Color.White
                        ),
                        enabled = !isAnimating
                    ) {
                        Text(
                            text = if (isAnimating) "กำลังสุ่ม..." else "สุ่มตัวเลข",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    if (errorMessage.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = errorMessage,
                            color = Color(0xFFE85C87),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = accent)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 28.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ตัวเลขที่ได้",
                        color = Color.White.copy(alpha = 0.92f),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = if (displayNumber.isBlank()) "--" else displayNumber,
                        modifier = Modifier
                            .scale(resultScale.value)
                            .alpha(resultAlpha.value),
                        color = Color.White,
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "จากช่วง $minNumber - $maxNumber",
                        color = Color.White.copy(alpha = 0.92f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "• ประวัติการสุ่ม",
                        color = textPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (history.isEmpty()) {
                        Text(
                            text = "ยังไม่มีประวัติ",
                            color = textSecondary,
                            fontSize = 15.sp
                        )
                    } else {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            history.forEach { item ->
                                Box(
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .background(
                                            color = if (item.toString() == result) {
                                                if (isDarkMode) Color(0xFF5A4450) else Color(0xFFFBE7EE)
                                            } else {
                                                chipBg
                                            },
                                            shape = RoundedCornerShape(22.dp)
                                        )
                                        .padding(horizontal = 20.dp, vertical = 16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.toString(),
                                        color = if (item.toString() == result) accent else textPrimary,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        NumberBottomNavBar(
            onHomeClick = onHomeClick,
            onNameClick = onNameClick,
            onNumberClick = { },
            onFoodClick = onFoodClick,
            onProfileClick = onProfileClick,
            barBg = bottomBarBg,
            normalColor = bottomBarText
        )
    }
}

@Composable
private fun NumberValueBox(
    value: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier,
    boxBg: Color,
    textColor: Color
) {
    Row(
        modifier = modifier
            .height(104.dp)
            .background(
                boxBg,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value.toString(),
            modifier = Modifier.weight(1f),
            color = textColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Black
        )

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = onIncrease,
                modifier = Modifier.size(34.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Increase",
                    tint = textColor
                )
            }

            IconButton(
                onClick = onDecrease,
                modifier = Modifier.size(34.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Decrease",
                    tint = textColor
                )
            }
        }
    }
}

@Composable
private fun NumberBottomNavBar(
    onHomeClick: () -> Unit,
    onNameClick: () -> Unit,
    onNumberClick: () -> Unit,
    onFoodClick: () -> Unit,
    onProfileClick: () -> Unit,
    barBg: Color,
    normalColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                barBg,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .navigationBarsPadding()
            .padding(horizontal = 12.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        NumberBottomNavItem("หน้าแรก", "⌂", false, onHomeClick, normalColor)
        NumberBottomNavItem("ชื่อ", "☺", false, onNameClick, normalColor)
        NumberBottomNavItem("ตัวเลข", "◉", true, onNumberClick, normalColor)
        NumberBottomNavItem("อาหาร", "☕", false, onFoodClick, normalColor)
        NumberBottomNavItem("โปรไฟล์", "◌", false, onProfileClick, normalColor)
    }
}

@Composable
private fun NumberBottomNavItem(
    label: String,
    icon: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    normalColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = icon,
            fontSize = 18.sp,
            color = if (isSelected) Color(0xFFE14D77) else normalColor,
            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = if (isSelected) Color(0xFFE14D77) else normalColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}