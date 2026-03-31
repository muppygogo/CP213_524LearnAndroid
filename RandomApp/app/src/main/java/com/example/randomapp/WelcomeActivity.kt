package com.example.randomapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = getSharedPreferences("session", MODE_PRIVATE)
        if (session.getString("loggedInEmail", null) != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContent {
            WelcomeScreen(
                onGetStarted = {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            )
        }
    }
}

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3B6C7)) // พื้นหลังสีชมพู
    ) {

        DecorativeWheel(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = 120.dp, y = 40.dp)
                .size(380.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 36.dp)
        ) {

            Spacer(modifier = Modifier.height(70.dp))

            Text(
                text = "Pickly.", // Pickly.
                color = Color.White,
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Can’t decide?",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Let the app choose for you in seconds.",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ปุ่ม Get started
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .width(220.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // white box
                    contentColor = Color(0xFFF48FB1) // pink text
                ),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun BottomGlow() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .offset(y = 40.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color(0xFFBFEFFF).copy(alpha = 0.28f)
                    )
                )
            )
    )
}

@Composable
fun DecorativeWheel(modifier: Modifier = Modifier) {
    val wheelColors = listOf(
        Color(0xFF8ED8EA), // aqua
        Color(0xFFF4C08F), // peach
        Color(0xFFE98FBF), // pink
        Color(0xFFB89AEF)  // purple
    )

    Canvas(modifier = modifier) {
        val diameter = size.minDimension
        val radius = diameter / 2f
        val center = Offset(size.width / 2f, size.height / 2f)

        val wheelSize = Size(diameter, diameter)
        val topLeft = Offset(center.x - radius, center.y - radius)

        val sliceCount = 16
        val sweep = 360f / sliceCount

        for (i in 0 until sliceCount) {
            rotate(i * sweep, pivot = center) {
                drawArc(
                    color = wheelColors[i % wheelColors.size],
                    startAngle = -90f,
                    sweepAngle = sweep,
                    useCenter = true,
                    topLeft = topLeft,
                    size = wheelSize
                )
            }
        }

        drawCircle(
            color = Color(0xFFF9E5EC),
            radius = diameter * 0.12f,
            center = center
        )

        drawCircle(
            color = Color(0xFFFFD4B0),
            radius = diameter * 0.07f,
            center = center
        )

        drawCircle(
            color = Color.White.copy(alpha = 0.55f),
            radius = diameter * 0.022f,
            center = Offset(center.x - diameter * 0.03f, center.y - diameter * 0.02f)
        )
    }
}
