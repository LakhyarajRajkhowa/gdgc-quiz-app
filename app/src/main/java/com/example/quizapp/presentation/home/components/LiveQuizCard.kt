package com.example.quizapp.presentation.home.components

import android.R.attr.text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

private val SoftYellow = Color(0xFFF6C66B)

@Composable
fun LiveQuizCard(title: String?, onJoinLiveQuiz: () -> Unit) {
    // if (title == null) return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SoftYellow)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Reminder",
                        tint = Color.Black,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Reminder",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                if (title == null)
                    Text(

                        text = "No Quiz is Live Now!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                else Text(

                    text = "${title} is Live Now!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = onJoinLiveQuiz,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.width(110.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                ) {
                    Text("Join Quiz", color = Color.Black)
                }
            }

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.reminder),
                    contentDescription = "Reminder",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}