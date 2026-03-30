package com.example.randomapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF6F8F4)
                ) {
                    MainMenuScreen(
                        onFoodClick = {
                            startActivity(Intent(this, FoodActivity::class.java))
                        },
                        onNameClick = {
                            startActivity(Intent(this, NameActivity::class.java))
                        },
                        onNumberClick = {
                            startActivity(Intent(this, NumberActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainMenuScreen(
    onFoodClick: () -> Unit,
    onNameClick: () -> Unit,
    onNumberClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F8F4))
    ) {

        FancyBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Random App",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2F3E30)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "minimal • elegant • lifestyle",
                fontSize = 14.sp,
                color = Color(0xFF8A9B8C)
            )

            Spacer(modifier = Modifier.height(50.dp))

            LuxuryButton("สุ่มอาหาร", onFoodClick)
            Spacer(modifier = Modifier.height(20.dp))

            LuxuryButton("สุ่มชื่อ", onNameClick)
            Spacer(modifier = Modifier.height(20.dp))

            LuxuryButton("สุ่มตัวเลข", onNumberClick)

            Spacer(modifier = Modifier.weight(1f))

            BottomLuxury()
        }
    }
}

@Composable
fun LuxuryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .shadow(10.dp, RoundedCornerShape(22.dp)),
        shape = RoundedCornerShape(22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xFFA8C8A2),
                            Color(0xFF7FA98A)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@Composable
fun BottomLuxury() {
    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFE7EFE4))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "กลับเมนู",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF6F7E71)
        )
    }
}

@Composable
fun FancyBackground() {
    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(x = 240.dp, y = (-40).dp)
                .clip(CircleShape)
                .background(Color(0x209FC5A8))
        )

        Box(
            modifier = Modifier
                .size(160.dp)
                .offset(x = (-40).dp, y = 120.dp)
                .clip(CircleShape)
                .background(Color(0x159FC5A8))
        )

        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(x = 260.dp, y = 600.dp)
                .clip(CircleShape)
                .background(Color(0x109FC5A8))
        )

        Box(
            modifier = Modifier
                .width(140.dp)
                .height(20.dp)
                .offset(x = 30.dp, y = 720.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0x0F7FA98A))
        )
    }
}