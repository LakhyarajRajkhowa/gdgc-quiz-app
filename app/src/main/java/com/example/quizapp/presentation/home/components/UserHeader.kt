package com.example.quizapp.presentation.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

private val Purple1 = Color(0xFF7D4CFF)
private val Purple2 = Color(0xFF6A3DF0)

@Composable
fun UserHeader(username: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(
                brush = androidx.compose.ui.graphics.Brush.linearGradient(
                    listOf(
                        Purple1,
                        Purple2
                    )
                )
            )
    ) {
        // decorative circles
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height
            drawCircle(
                color = Color.White.copy(alpha = 0.08f),
                radius = w * 0.45f,
                center = Offset(x = w * 0.8f, y = -h * 0.1f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.06f),
                radius = w * 0.6f,
                center = Offset(x = w * 0.2f, y = h * 0.2f)
            )
        }

        Column(modifier = Modifier.padding(start = 20.dp, top = 56.dp)) {
            Text(
                text = "Hello ${username}",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "We are happy to have you back",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        }

        IconButton(
            onClick = { /* trophy */ },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 16.dp)
                .size(42.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_trophy_24),
                contentDescription = "profile",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}