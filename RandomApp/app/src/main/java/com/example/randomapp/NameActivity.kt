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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomapp.ui.theme.RandomAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settings = getSharedPreferences("app_settings", MODE_PRIVATE)
        val darkMode = settings.getBoolean("dark_mode_enabled", false)

        setContent {
            RandomAppTheme(
                darkTheme = darkMode,
                dynamicColor = false
            ) {
                NameScreen(
                    isDarkMode = darkMode,
                    onBack = { finish() },
                    onHomeClick = {
                        startActivity(Intent(this, MainActivity::class.java))
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

private val nameListSaver = listSaver<SnapshotStateList<String>, String>(
    save = { it.toList() },
    restore = { restored ->
        mutableStateListOf<String>().apply { addAll(restored) }
    }
)

@Composable
fun NameScreen(
    isDarkMode: Boolean,
    onBack: () -> Unit,
    onHomeClick: () -> Unit,
    onNumberClick: () -> Unit,
    onFoodClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var inputText by rememberSaveable { mutableStateOf("") }
    val names = rememberSaveable(saver = nameListSaver) { mutableStateListOf<String>() }

    var displayText by rememberSaveable { mutableStateOf("") }
    var removeAfterPick by rememberSaveable { mutableStateOf(true) }
    var isAnimating by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }
    val alpha = remember { Animatable(1f) }

    val pageBg = if (isDarkMode) Color(0xFF1E1B22) else Color(0xFFF1DCE4)
    val cardBg = if (isDarkMode) Color(0xFF362F3D) else Color(0xFFF8F6F7)
    val inputBg = if (isDarkMode) Color(0xFF463D4D) else Color(0xFFF5E8EE)
    val textPrimary = if (isDarkMode) Color(0xFFF6EAF0) else Color(0xFF6E5663)
    val textSecondary = if (isDarkMode) Color(0xFFD2B9C6) else Color(0xFFA98899)
    val chipBg = if (isDarkMode) Color(0xFF4A404F) else Color(0xFFF5E8EE)
    val accent = Color(0xFFE85C87)
    val unselectedButtonBg = if (isDarkMode) Color(0xFF4A404F) else Color(0xFFF5E8EE)
    val unselectedButtonText = if (isDarkMode) Color(0xFFD8C0CC) else Color(0xFF8E6F80)
    val bottomBarBg = if (isDarkMode) Color(0xFF2E2833) else Color(0xFFF8EFF3)
    val bottomBarText = if (isDarkMode) Color(0xFFC5AAB8) else Color(0xFF8E6F80)

    fun addName() {
        val text = inputText.trim()
        if (text.isNotEmpty()) {
            names.add(text)
            inputText = ""
        }
    }

    fun randomName() {
        if (names.isEmpty()) return

        scope.launch {
            isAnimating = true

            repeat(20) {
                displayText = names.random()
                delay(40)
            }

            repeat(8) {
                displayText = names.random()
                delay(90)
            }

            val selected = names.random()
            displayText = selected

            if (removeAfterPick) {
                names.remove(selected)
            }

            scale.snapTo(0.9f)
            alpha.snapTo(0.5f)

            launch {
                scale.animateTo(1.1f, tween(150))
                scale.animateTo(1f, tween(200, easing = FastOutSlowInEasing))
            }

            launch {
                alpha.animateTo(1f, tween(200))
            }

            isAnimating = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBg)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text(
                text = "← กลับ",
                modifier = Modifier.clickable { onBack() },
                color = textSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "สุ่มรายชื่อ",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textPrimary
            )

            Text(
                text = "ใส่ชื่อแล้วกดสุ่มได้เลย",
                color = textSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "• ใส่รายชื่อ",
                        fontWeight = FontWeight.Bold,
                        color = textPrimary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = { Text("พิมพ์ชื่อแล้วกด Enter", color = textSecondary) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        keyboardActions = KeyboardActions(onDone = { addName() }),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = inputBg,
                            unfocusedContainerColor = inputBg,
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = accent
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = { addName() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = accent)
                    ) {
                        Text("เพิ่มชื่อ", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "• โหมดการสุ่ม",
                        fontWeight = FontWeight.Bold,
                        color = textPrimary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row {
                        Button(
                            onClick = { removeAfterPick = true },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (removeAfterPick) accent else unselectedButtonBg,
                                contentColor = if (removeAfterPick) Color.White else unselectedButtonText
                            )
                        ) {
                            Text("สุ่มแล้วลบออก")
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Button(
                            onClick = { removeAfterPick = false },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (!removeAfterPick) accent else unselectedButtonBg,
                                contentColor = if (!removeAfterPick) Color.White else unselectedButtonText
                            )
                        ) {
                            Text("สุ่มแล้วคงอยู่")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { randomName() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = accent),
                        enabled = !isAnimating
                    ) {
                        Text(if (isAnimating) "กำลังสุ่ม..." else "สุ่มชื่อ", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (displayText.isBlank()) "ยังไม่ได้สุ่ม" else displayText,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .scale(scale.value)
                            .alpha(alpha.value),
                        color = accent
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "• รายชื่อทั้งหมด",
                        fontWeight = FontWeight.Bold,
                        color = textPrimary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    if (names.isEmpty()) {
                        Text(
                            text = "ยังไม่มีรายชื่อ",
                            color = textSecondary
                        )
                    } else {
                        names.forEach { name ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                                    .background(chipBg, RoundedCornerShape(14.dp))
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(name, color = textPrimary)

                                Text(
                                    text = "✕",
                                    color = accent,
                                    modifier = Modifier.clickable {
                                        names.remove(name)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }

        NameBottomNavBar(
            onHomeClick = onHomeClick,
            onNameClick = { },
            onNumberClick = onNumberClick,
            onFoodClick = onFoodClick,
            onProfileClick = onProfileClick,
            selected = "name",
            barBg = bottomBarBg,
            normalColor = bottomBarText
        )
    }
}

@Composable
private fun NameBottomNavBar(
    onHomeClick: () -> Unit,
    onNameClick: () -> Unit,
    onNumberClick: () -> Unit,
    onFoodClick: () -> Unit,
    onProfileClick: () -> Unit,
    selected: String,
    barBg: Color,
    normalColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(barBg)
            .navigationBarsPadding()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NameNavItem("หน้าแรก", "⌂", selected == "home", onHomeClick, normalColor)
        NameNavItem("ชื่อ", "☺", selected == "name", onNameClick, normalColor)
        NameNavItem("ตัวเลข", "◉", selected == "number", onNumberClick, normalColor)
        NameNavItem("อาหาร", "☕", selected == "food", onFoodClick, normalColor)
        NameNavItem("โปรไฟล์", "◌", selected == "profile", onProfileClick, normalColor)
    }
}

@Composable
private fun NameNavItem(
    label: String,
    icon: String,
    selected: Boolean,
    onClick: () -> Unit,
    normalColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = icon,
            color = if (selected) Color(0xFFE85C87) else normalColor,
            fontSize = 18.sp
        )
        Text(
            text = label,
            color = if (selected) Color(0xFFE85C87) else normalColor,
            fontSize = 12.sp
        )
    }
}