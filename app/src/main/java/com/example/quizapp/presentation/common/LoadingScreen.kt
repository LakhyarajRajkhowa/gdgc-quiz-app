package com.example.quizapp.presentation.common


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizapp.R
import kotlinx.coroutines.delay

@Composable
fun QuizLoadingScreen() {
    val purple = Color(0xFF8956F0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(purple),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Preparing your Quiz...",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.quiz_prep),
                contentDescription = "Preparing illustration",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.4f)
            )

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = "Loading",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )


            Image(
                painter = painterResource(id = R.drawable.loading),
                contentDescription = "Preparing illustration",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            )
        }
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height
            drawCircle(
                color = Color.White.copy(alpha = 0.09f),
                radius = w * 0.3f,
                center = Offset(x = w * 0.86f, y = h * 0.935f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.09f),
                radius = w * 0.4f,
                center = Offset(x = w * 0.1f, y = h * 0.02f)
            )
        }

    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 852)
@Composable
fun QuizLoadingScreenPreview() {
    QuizLoadingScreen()
}

@Composable
fun LoadingScreen() {
    val purple = Color(0xFF8956F0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(purple),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Loading...",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.quiz_prep),
                contentDescription = "Preparing illustration",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.4f)
            )
        }
        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height
            drawCircle(
                color = Color.White.copy(alpha = 0.09f),
                radius = w * 0.3f,
                center = Offset(x = w * 0.86f, y = h * 0.935f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.09f),
                radius = w * 0.4f,
                center = Offset(x = w * 0.1f, y = h * 0.02f)
            )
        }

    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 852)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}
