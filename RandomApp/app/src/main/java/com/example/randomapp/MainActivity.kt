package com.example.randomapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.Icon

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = getSharedPreferences("session", MODE_PRIVATE)
        val displayName = session.getString("loggedInName", "Username") ?: "Username"
        val savedProfileUri = session.getString("profileImageUri", null)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF3DADF)
                ) {
                    MainMenuScreen(
                        username = displayName,
                        initialProfileUri = savedProfileUri,
                        onSaveProfileUri = { uriString ->
                            session.edit().putString("profileImageUri", uriString).apply()
                        },
                        onFoodClick = {
                            startActivity(Intent(this, FoodActivity::class.java))
                        },
                        onNameClick = {
                            startActivity(Intent(this, NameActivity::class.java))
                        },
                        onNumberClick = {
                            startActivity(Intent(this, NumberActivity::class.java))
                        },
                        onLogout = {
                            session.edit().clear().apply()
                            startActivity(Intent(this, WelcomeActivity::class.java))
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MainMenuScreen(
    username: String,
    initialProfileUri: String?,
    onSaveProfileUri: (String?) -> Unit,
    onFoodClick: () -> Unit,
    onNameClick: () -> Unit,
    onNumberClick: () -> Unit,
    onLogout: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var profileUri by remember { mutableStateOf(initialProfileUri) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            profileUri = it.toString()
            onSaveProfileUri(it.toString())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3DADF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Box {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(22.dp))
                            .background(Color(0xFFF8F8F8))
                            .clickable { expanded = true }
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = username,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        if (profileUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(profileUri),
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFD7D9E2)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Default Profile",
                                    tint = Color(0xFF9AA0AE),
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Menu",
                            tint = Color.Gray
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Change profile photo") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.PhotoCamera,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                expanded = false
                                imagePickerLauncher.launch("image/*")
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Logout") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ExitToApp,
                                    contentDescription = null
                                )
                            },
                            onClick = {
                                expanded = false
                                onLogout()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            MenuCard(
                title = "Pick a name",
                cardColor = Color(0xFFD1AAB1),
                circleColor = Color(0xFF7FD0FF),
                emoji = "👩",
                onClick = onNameClick
            )

            Spacer(modifier = Modifier.height(28.dp))

            MenuCard(
                title = "Pick a number",
                cardColor = Color(0xFFF7F7F7),
                titleColor = Color(0xFFD1AAB1),
                circleColor = Color(0xFFEC69B8),
                emoji = "9",
                onClick = onNumberClick
            )

            Spacer(modifier = Modifier.height(28.dp))

            MenuCard(
                title = "Pick a food",
                cardColor = Color(0xFFD1AAB1),
                circleColor = Color(0xFF7FD0FF),
                emoji = "🍜",
                onClick = onFoodClick
            )
        }
    }
}

@Composable
fun MenuCard(
    title: String,
    cardColor: Color,
    circleColor: Color,
    emoji: String,
    onClick: () -> Unit,
    titleColor: Color = Color.White
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(165.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(cardColor)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 24.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = titleColor,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .size(122.dp)
                .clip(CircleShape)
                .background(circleColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                fontSize = if (emoji == "9") 64.sp else 54.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}