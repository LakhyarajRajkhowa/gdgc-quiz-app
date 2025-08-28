package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

data class LeaderboardEntry(
    val rank: Int,
    val name: String,
    val score: Int
)

@Composable
fun LeaderboardScreen(
    leaderboard: List<LeaderboardEntry>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF7D4CFF))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            drawCircle(
                color = Color.White.copy(alpha = 0.09f),
                radius = width * 0.3f,
                center = Offset(x = width * 0.1f, y = height * 0.935f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.09f),
                radius = width * 0.4f,
                center = Offset(x = width * 0.86f, y = height * 0.02f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Leaderboard",
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Leaderboard Card
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Box(
                            modifier = Modifier.weight(0.2f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Rank",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.Gray
                            )
                        }

                        Box(
                            modifier = Modifier.weight(0.6f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Name",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.Gray
                            )
                        }

                        Box(
                            modifier = Modifier.weight(0.2f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Score",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    // Leaderboard entries
                    leaderboard.forEach { entry ->
                        LeaderboardRow(entry)
                        if (entry != leaderboard.last()) {
                            Divider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LeaderboardRow(entry: LeaderboardEntry) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.weight(0.2f),
            contentAlignment = Alignment.Center
        ) {
            when (entry.rank) {
                1 -> Image(
                    painter = painterResource(id = R.drawable.gold_trophy),
                    contentDescription = "1st place",
                    modifier = Modifier.size(32.dp)
                )
                2 -> Image(
                    painter = painterResource(id = R.drawable.silver_trophy),
                    contentDescription = "2nd place",
                    modifier = Modifier.size(32.dp)
                )
                3 -> Image(
                    painter = painterResource(id = R.drawable.bronze_trophy),
                    contentDescription = "3rd place",
                    modifier = Modifier.size(32.dp)
                )
                else -> Text(
                    text = "${entry.rank}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

        Box(
            modifier = Modifier.weight(0.6f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = entry.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }

        Box(
            modifier = Modifier.weight(0.2f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = entry.score.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF7D4CFF)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LeaderboardPreview() {
    MaterialTheme {
        val sampleData = listOf(
            LeaderboardEntry(1, "Alex Johnson", 95),
            LeaderboardEntry(2, "Maria Garcia", 88),
            LeaderboardEntry(3, "James Wilson", 82),
            LeaderboardEntry(4, "Sarah Miller", 78),
            LeaderboardEntry(5, "Robert Davis", 75),
            LeaderboardEntry(6, "Emily Brown", 72),
            LeaderboardEntry(7, "Michael Taylor", 68),
        )
        LeaderboardScreen(sampleData)
    }
}