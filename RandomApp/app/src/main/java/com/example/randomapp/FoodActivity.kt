package com.example.randomapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

class FoodActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF3DADF)
                ) {
                    val vm: FoodViewModel = viewModel(factory = FoodViewModelFactory())
                    val uiState by vm.uiState.collectAsState()

                    FoodScreen(
                        uiState = uiState,
                        onCategorySelected = vm::setCategory,
                        onRandomClick = vm::randomFood,
                        onMoodSelected = vm::setMood,
                        onAiSuggestClick = vm::aiSuggestByMood,
                        onShowError = { msg ->
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FoodScreen(
    uiState: FoodUiState,
    onCategorySelected: (FoodCategory) -> Unit,
    onRandomClick: () -> Unit,
    onMoodSelected: (String) -> Unit,
    onAiSuggestClick: () -> Unit,
    onShowError: (String) -> Unit
) {
    LaunchedEffect(uiState.error) {
        uiState.error?.let { onShowError(it) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFF3DADF),
                        Color(0xFFF7E5E9),
                        Color(0xFFFDF8FA)
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Pick a Food",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF21171A)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FoodCategoryChip("Food", uiState.selectedCategory == FoodCategory.FOOD) {
                onCategorySelected(FoodCategory.FOOD)
            }
            FoodCategoryChip("Dessert", uiState.selectedCategory == FoodCategory.DESSERT) {
                onCategorySelected(FoodCategory.DESSERT)
            }
            FoodCategoryChip("Drink", uiState.selectedCategory == FoodCategory.DRINK) {
                onCategorySelected(FoodCategory.DRINK)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD1AAB1))
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "Result",
                    color = Color.White.copy(alpha = 0.95f),
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (!uiState.result?.imageUrl.isNullOrBlank()) {
                    Image(
                        painter = rememberAsyncImagePainter(uiState.result?.imageUrl),
                        contentDescription = uiState.result?.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                }

                SlotMachineText(
                    text = uiState.result?.name ?: "Ready to random 🍽️"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onRandomClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF84D3FF),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (uiState.isLoading) "Loading..." else "🎰 Random Food",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "AI Suggest by Mood",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF21171A)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MoodChip("Happy", uiState.selectedMood == "Happy") { onMoodSelected("Happy") }
            MoodChip("Sad", uiState.selectedMood == "Sad") { onMoodSelected("Sad") }
            MoodChip("Lazy", uiState.selectedMood == "Lazy") { onMoodSelected("Lazy") }
            MoodChip("Romantic", uiState.selectedMood == "Romantic") { onMoodSelected("Romantic") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onAiSuggestClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text("🧠 AI Suggest")
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = when (uiState.selectedCategory) {
                FoodCategory.FOOD -> "Food uses TheMealDB real API."
                FoodCategory.DESSERT -> "Dessert uses TheMealDB real API."
                FoodCategory.DRINK -> "Drink is local fallback for now. Next step we can connect a real drinks API."
            },
            color = Color.Gray,
            fontSize = 13.sp
        )
    }
}

@Composable
fun FoodCategoryChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text) }
    )
}

@Composable
fun MoodChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text) }
    )
}

@Composable
fun SlotMachineText(text: String) {
    val offset = remember { Animatable(0f) }

    LaunchedEffect(text) {
        offset.snapTo(-80f)
        offset.animateTo(
            targetValue = 0f,
            animationSpec = tween(400, easing = FastOutSlowInEasing)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            modifier = Modifier.offset(y = offset.value.dp)
        )
    }
}