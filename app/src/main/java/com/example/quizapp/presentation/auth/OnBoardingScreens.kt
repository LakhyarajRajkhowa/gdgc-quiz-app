package com.example.quizapp.presentation.auth

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizapp.R

@Composable
fun OnboardingScreen(
    @DrawableRes imageRes: Int,
    title: String? = null,
    description: String? = null,
    button1Text: String,
    onButton1Click: () -> Unit,
    button2Text: String? = null,
    onButton2Click: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8956F0))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (title != null) {
                Text(text = title, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.5f)
            )

            Spacer(modifier = Modifier.height(22.dp))
            if (description != null) {
                Text(
                    text = description,
                    fontSize = 15.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }


            Spacer(modifier = Modifier.fillMaxHeight(0.35f))

            // Primary Button
            Button(
                onClick = onButton1Click,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(button1Text, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            // Optional Secondary Button
            if (button2Text != null && onButton2Click != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onButton2Click,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Color.White),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    Text(button2Text, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
        }

        Canvas(modifier = Modifier.matchParentSize()) {
            val w = size.width
            val h = size.height
            drawCircle(
                color = Color.White.copy(alpha = 0.09f),
                radius = w * 0.3f,
                center = Offset(x = w * 0.1f, y = h * 0.935f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.09f),
                radius = w * 0.4f,
                center = Offset(x = w * 0.86f, y = h * 0.02f)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Onboarding1Preview() {
    OnboardingScreen(
        imageRes = R.drawable.onboarding1,
        title = "Let’s get started!",
        button1Text = "Explore now!",
        onButton1Click = {},
        button2Text = "Login",
        onButton2Click = {}
    )
}

@Preview(showBackground = true)
@Composable
fun Onboarding2Preview() {
    OnboardingScreen(
        imageRes = R.drawable.onboarding2,
        title = "Let’s get started!",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
        button1Text = "Next",
        onButton1Click = {}
    )
}

@Preview(showBackground = true)
@Composable
fun Onboarding3Preview() {
    OnboardingScreen(
        imageRes = R.drawable.onboarding3,
        title = "Let’s get started!",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
        button1Text = "Next",
        onButton1Click = {}
    )
}

@Preview(showBackground = true)
@Composable
fun Onboarding4Preview() {
    OnboardingScreen(
        imageRes = R.drawable.onboarding4,
        title = "Let’s get started!",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
        button1Text = "Get Started!",
        onButton1Click = {}
    )
}
