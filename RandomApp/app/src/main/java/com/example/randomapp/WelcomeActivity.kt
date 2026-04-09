package com.example.randomapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.randomapp.ui.theme.RandomAppTheme

class WelcomeActivity : ComponentActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(this)

        // ถ้า login อยู่แล้ว ให้ข้ามหน้า Welcome ไปหน้า Main เลย
        if (sessionManager.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        setContent {
            RandomAppTheme {
                WelcomeScreen(
                    onStartClick = {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    onStartClick: () -> Unit
) {
    val bgGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFF8D6E0),
            Color(0xFFF7B3C8),
            Color(0xFFF58CAB)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
    ) {
        GlowCircle(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.BottomStart)
                .padding(start = 10.dp, bottom = 150.dp),
            color = Color.White.copy(alpha = 0.14f)
        )

        GlowCircle(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.Center)
                .padding(top = 260.dp),
            color = Color.White.copy(alpha = 0.10f)
        )

        DotsDecoration(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 95.dp, end = 34.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            WheelGraphic()

            Spacer(modifier = Modifier.size(78.dp))

            Text(
                text = "Pickly.",
                style = TextStyle(
                    fontSize = 54.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = FontFamily.Cursive
                )
            )

            Spacer(modifier = Modifier.size(18.dp))

            Text(
                text = "ตัดสินใจไม่ได้?",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                text = "ให้แอปช่วยเลือกให้คุณ ในไม่กี่วินาที",
                color = Color.White.copy(alpha = 0.94f),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.size(84.dp))

            Button(
                onClick = onStartClick,
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFFFF4F87)
                ),
                contentPadding = PaddingValues(horizontal = 48.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "เริ่มเลย",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun GlowCircle(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier.background(
            brush = Brush.radialGradient(
                colors = listOf(
                    color,
                    Color.Transparent
                )
            ),
            shape = RoundedCornerShape(999.dp)
        )
    )
}

@Composable
private fun DotsDecoration(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(width = 170.dp, height = 150.dp)) {
        val dotColor = Color.White.copy(alpha = 0.50f)
        val stepX = size.width / 8f
        val stepY = size.height / 8f

        for (row in 0..7) {
            for (col in 0..7) {
                drawCircle(
                    color = dotColor,
                    radius = 4f,
                    center = Offset(
                        x = col * stepX + 10f,
                        y = row * stepY + 10f
                    )
                )
            }
        }
    }
}

@Composable
private fun WheelGraphic() {
    Canvas(
        modifier = Modifier.size(275.dp)
    ) {
        val wheelColors = listOf(
            Color(0xFF9FD7C4),
            Color(0xFFF4AFC2),
            Color(0xFF9AC6DB),
            Color(0xFFC0B2DF),
            Color(0xFFF1CD99),
            Color(0xFFF17D9A)
        )

        val sweep = 360f / wheelColors.size
        val wheelSize = Size(size.width, size.height)

        for (i in wheelColors.indices) {
            rotate(degrees = i * sweep) {
                drawArc(
                    color = wheelColors[i],
                    startAngle = -90f,
                    sweepAngle = sweep,
                    useCenter = true,
                    topLeft = Offset.Zero,
                    size = wheelSize
                )
            }
        }

        drawCircle(
            color = Color.White.copy(alpha = 0.15f),
            radius = size.minDimension * 0.33f
        )

        drawCircle(
            color = Color.White.copy(alpha = 0.52f),
            radius = size.minDimension * 0.10f
        )

        drawCircle(
            color = Color(0xFFF7EDEE),
            radius = size.minDimension * 0.06f
        )

        drawCircle(
            color = Color(0xFFE6B5C1),
            radius = size.minDimension * 0.024f
        )
    }
}