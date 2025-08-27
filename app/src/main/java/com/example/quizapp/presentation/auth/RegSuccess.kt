package com.example.quizapp.presentation.auth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R
import kotlin.random.Random
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.drawscope.rotate

@Composable
fun SuccessScreen(onContinueClick: () -> Unit) {

    // Extension functions for random ranges
    fun ClosedFloatingPointRange<Float>.random() =
        Random.nextFloat() * (endInclusive - start) + start

    fun ClosedRange<Int>.random() =
        Random.nextInt(start, endInclusive + 1)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8956F0))
    ) {
        // Confetti drawn with Canvas
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .align(Alignment.TopCenter)
        ) {
            val colors = listOf(
                Color.Yellow, Color.Red, Color.Green, Color.Magenta, Color(0xFFFF9800),
                Color.Blue, Color.Cyan, Color(0xFFFFEB3B), Color(0xFF9C27B0)
            )
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Draw randomly scattered confetti pieces with different shapes
            for (i in 0..90) {
                val x = (0f..canvasWidth).random()
                val y = (0f..canvasHeight).random()
                val sizeVal = (16f..26f).random()
                val rotation = (0f..360f).random()
                val color = colors.random()

                withTransform({
                    rotate(rotation, Offset(x + sizeVal/2, y + sizeVal/2))
                }) {
                    // Randomly choose between rectangle or circle
                    when ((0..1).random()) {
                        0 -> drawRect(
                            color = color,
                            topLeft = Offset(x, y),
                            size = androidx.compose.ui.geometry.Size(sizeVal, sizeVal)
                        )
                        1 -> drawCircle(
                            color = color,
                            center = Offset(x, y),
                            radius = sizeVal / 2
                        )
                    }
                }
            }
        }

        // Background Circles
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            drawCircle(
                color = Color.White.copy(alpha = 0.09f),
                radius = w * 0.4f,
                center = Offset(x = w * 0.2f, y = h * 0.9f)
            )

            drawCircle(
                color = Color.White.copy(alpha = 0.09f),
                radius = w * 0.35f,
                center = Offset(x = w * 0.8f, y = h * 0.95f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(100.dp)) // space below confetti

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "YAY!",
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "You have successfully\nregistered to your account",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            // Continue Button
            Button(
                onClick = onContinueClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Continue",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessScreenPreview() {
    SuccessScreen(onContinueClick = {})
}
