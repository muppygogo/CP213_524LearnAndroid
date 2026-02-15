package com.example.a524_lablearnandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class RPGCardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Lifecycle", "MainActivity : onCreate")
        enableEdgeToEdge()
        setContent {
            RPGCardView()
        }
    }
    override fun onStart() {
        super.onStart()
        Log.i("Lifecycle", "MainActivity : onStart")
    }
    override fun onResume() {
        super.onResume()
        //load จะอยุ่ตรงนี้เพื่อ
        Log.i("Lifecycle", "MainActivity : onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.i("Lifecycle", "MainActivity : onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.i("Lifecycle", "MainActivity : onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Lifecycle", "MainActivity : onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Lifecycle", "MainActivity : onRestart")
    }

    @Composable
    fun RPGCardView(){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp)
        ){

            //hp
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(Color.Gray)
            ){
                Text(
                    text = "hp",
                    modifier = Modifier
                        .align(alignment = Alignment.CenterStart)
                        .fillMaxWidth(fraction = 0.69f)
                        .background(Color.Red)
                        .padding(9.dp)
                )
            }
            //My image
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(650.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
                    .clickable {
                        startActivity(Intent(this@RPGCardActivity, MainActivity2::class.java))
                    }
            )
            //status
            var Str by remember { mutableStateOf(99) }
            var Agi by remember { mutableStateOf(99) }
            var Int by remember { mutableStateOf(99) }
            Row (modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column(modifier = Modifier.padding(start = 20.dp)){
                    Button(onClick = {
                        Str = Str+1 //str++
                    }) {
                        Text(text = "+", fontSize = 16.sp)
                    }
                    Row {
                        Text(text = "Str", fontSize = 32.sp ,fontWeight = FontWeight.SemiBold)
                        Image(
                            painter = painterResource(id = R.drawable.muscle),
                            contentDescription = "muscle",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Text(text = Str.toString(), fontSize = 32.sp)
                    Image(
                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                        contentDescription = "-",
                        modifier = Modifier.size(32.dp)
                            .clickable {
                                Str = Str-1
                            }
                    )
                }
                Column {
                    Button(onClick = {
                        Agi = Agi+1 //str++
                    }) {
                        Text(text = "+", fontSize = 16.sp)
                    }
                    Row {
                        Text(text = "Agi", fontSize = 32.sp ,fontWeight = FontWeight.SemiBold)
                        Image(
                            painter = painterResource(id = R.drawable.running),
                            contentDescription = "running",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Text(text = Agi.toString(), fontSize = 32.sp)
                    Image(
                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                        contentDescription = "-",
                        modifier = Modifier.size(32.dp)
                            .clickable {
                                Agi = Agi-1
                            }
                    )
                }
                Column(modifier = Modifier.padding(end = 20.dp)){
                    Button(onClick = {
                        Int = Int+1 //Agi++
                    }) {
                        Text(text = "+", fontSize = 16.sp)
                    }
                    Row {
                        Text(text = "Int", fontSize = 32.sp ,fontWeight = FontWeight.SemiBold)
                        Image(
                            painter = painterResource(id = R.drawable.brain),
                            contentDescription = "muscle",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Text(text = Int.toString(), fontSize = 32.sp)
                    Image(
                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                        contentDescription = "-",
                        modifier = Modifier.size(32.dp)
                            .clickable {
                                Int = Int-1
                            }
                    )
                }

            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun RPGCARDPreview() {
        RPGCardView()
    }
}