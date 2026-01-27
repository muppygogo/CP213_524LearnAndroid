package com.example.a524_lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a524_lablearnandroid.ui.theme._524_LabLearnAndroidTheme
import com.example.a524_lablearnandroid.ui.theme._524_LabLearnAndroidTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .padding(32.dp)){
                //hp
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Color.White)

                ){
                    Text(
                        text = "hp",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterStart)
                            .fillMaxWidth(fraction = 0.5F)
                            .background(color =Color.Red)
                            .padding(8.dp)
                    )

                }
                //image

                //status
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _524_LabLearnAndroidTheme  {
        Greeting("Android")
    }
}