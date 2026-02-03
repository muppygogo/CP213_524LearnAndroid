package com.example.a524_lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray)
                    .padding(32.dp)
            ) {
                // hp
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .background(color = Color.White)
                        .padding(2.dp)
                ) {
                    Text(
                        text = "hp",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .fillMaxWidth(fraction = 0.524f)
                            .background(color = Color.Red)
                            .padding(6.dp)
                    )
                }

                // image
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "profile",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(16.dp)
                    )
                }

                // status
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Str", fontSize = 32.sp)
                        Text(text = "8", fontSize = 32.sp)
                    }

                    Column {
                        Text(text = "Agi", fontSize = 32.sp)
                        Text(text = "8", fontSize = 32.sp)
                    }

                    Column {
                        Text(text = "Int", fontSize = 32.sp)
                        Text(text = "8", fontSize = 32.sp)
                    }
                }
            }
        }
    }
}
