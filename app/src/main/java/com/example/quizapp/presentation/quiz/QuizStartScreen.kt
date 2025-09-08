package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

@Composable
fun QuizStartScreen(
    totalQuestions: Int = 10,
    secondsPerQuestion: Int = 30,
    onBack: () -> Unit = {},
    onBegin: () -> Unit = {},
) {

    Scaffold(
        topBar = {
            Surface(
                tonalElevation = 0.dp,
                color = Color(0xFF7D4CFF)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(start = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Questions:",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = totalQuestions.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
                )

                Text(
                    text = "Time for each question:",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = "$secondsPerQuestion seconds",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )

                Button(
                    onClick = onBegin,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(48.dp)
                        .width(240.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7D4CFF),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Begin Quiz!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.begin_quiz),
                contentDescription = "Illustration",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 90.dp)
                    .height(360.dp)
            )
        }
    }
}
