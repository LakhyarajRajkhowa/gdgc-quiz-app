package com.example.quizapp.presentation.common
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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

@Composable
fun NoInternetScreen(onRetryClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8956F0)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.no_internet),
                contentDescription = "No Internet",
                tint = Color.Black,
                modifier = Modifier
                    .size(190.dp)
                    .padding(bottom = 32.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))
            // Title
            Text(
                text = "Whoops!",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Description
            Text(
                text = "Looks like you’re offline. Please check your connection and try again.",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Retry Button
            Button(
                onClick = onRetryClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Retry",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height
            drawCircle(
                color = Color.White.copy(alpha = 0.04f),
                radius = w * 0.4f,
                center = Offset(x = w / 2f, y = h * 0.33f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.08f),
                radius = w * 0.3f,
                center = Offset(x = w / 2f, y = h * 0.33f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoInternetScreenPreview() {
    NoInternetScreen(onRetryClick = {})
}
