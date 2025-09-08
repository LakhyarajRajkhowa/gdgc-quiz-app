package com.example.quizapp.presentation.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

@Composable
fun ProgressSection(
    ranking: Int,
    totalScore: Int,
    quizzesAttempted: Int,
    coins: Int,
    progressPercent: Int
) {
    Text("This Week's Progress", fontWeight = FontWeight.SemiBold)
    Spacer(modifier = Modifier.height(5.dp))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F0FF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("My Ranking: 0", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Text("Total Scored earned: 0")
                Text("Quizzes attempted: 0")
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {

                }
            }

            // Circular progress with percentage number inside
            Box(
                modifier = Modifier.width(110.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    CircularPercentage(percentage = 0.00f, label = "0%")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Level:1",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }
}

@Composable
private fun CircularPercentage(
    percentage: Float,
    label: String,
    sizeDp: Dp = 100.dp
) {
    val sweep = percentage.coerceIn(0f, 1f) * 360f

    Box(modifier = Modifier.size(sizeDp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val stroke = Stroke(width = 10f, cap = StrokeCap.Round)
            // background ring
            drawArc(
                color = Color.LightGray.copy(alpha = 0.4f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )
            // active arc
            drawArc(
                color = Color(0xFF3CB371),
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                style = stroke
            )
        }

        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}