package com.example.quizapp.presentation.auth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class RegisterStep {
    USERNAME, SCHOLAR_ID, PASSWORD
}

@Composable
fun RegisterScreen(
    onFinish: (username: String, scholarId: String, password: String) -> Unit,
    onBackToLogin: () -> Unit
) {
    var currentStep by remember { mutableStateOf(RegisterStep.USERNAME) }
    var username by remember { mutableStateOf("") }
    var scholarId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // helper to go to previous step when back arrow pressed
    fun goBack() {
        currentStep = when (currentStep) {
            RegisterStep.USERNAME -> {
                onBackToLogin() // ✅ go back to login when already at first step
            RegisterStep.USERNAME
            }
            RegisterStep.SCHOLAR_ID -> RegisterStep.USERNAME
            RegisterStep.PASSWORD -> RegisterStep.SCHOLAR_ID
        }
    }

    val totalSteps = RegisterStep.entries.size
    val currentStepIndex = RegisterStep.entries.indexOf(currentStep) + 1
    val progress = currentStepIndex / totalSteps.toFloat()

    Box(modifier = Modifier.fillMaxSize()) {
        // decorative background
        DecorativeCircles()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(Modifier.height(16.dp))

            // Back Arrow
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .size(28.dp)
                    .clickable { goBack() }
            )

            // Purple underline
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = Color(0xFF512DA8)
                )
            }


            Spacer(modifier = Modifier.height(32.dp))

            // Title depending on step
            val title = when (currentStep) {
                RegisterStep.USERNAME -> "Enter your Username"
                RegisterStep.SCHOLAR_ID -> "Enter your Scholar ID"
                RegisterStep.PASSWORD -> "Set Your Password"
            }

            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input for each step (keeps original backend logic)
            when (currentStep) {
                RegisterStep.USERNAME -> {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        placeholder = { Text("Your username") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                RegisterStep.SCHOLAR_ID -> {
                    OutlinedTextField(
                        value = scholarId,
                        onValueChange = { scholarId = it },
                        placeholder = { Text("Your scholar id") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                RegisterStep.PASSWORD -> {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Your password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(0.01f))
        }

        // Action button pinned to bottom center
        val (buttonLabel, enabled) = when (currentStep) {
            RegisterStep.USERNAME -> Pair("Next", username.isNotBlank())
            RegisterStep.SCHOLAR_ID -> Pair("Next", scholarId.isNotBlank())
            RegisterStep.PASSWORD -> Pair("Finish Signup", password.isNotBlank())
        }

        Button(
            onClick = {
                when (currentStep) {
                    RegisterStep.USERNAME -> if (username.isNotBlank()) currentStep = RegisterStep.SCHOLAR_ID
                    RegisterStep.SCHOLAR_ID -> if (scholarId.isNotBlank()) currentStep = RegisterStep.PASSWORD
                    RegisterStep.PASSWORD -> if (password.isNotBlank()) onFinish(username, scholarId, password)
                }
            },
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(60.dp)
                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7D4CFF),
                contentColor = Color.White
            )
        ) {
            Text(buttonLabel, fontWeight = FontWeight.Bold, fontSize = 17.sp)
        }
    }
}

@Composable
fun DecorativeCircles() {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val width = size.width
        val height = size.height

        // Large faint circle bottom-left
        drawCircle(
            color = Color(0xFF7D4CFF).copy(alpha = 0.08f),
            radius = width * 0.6f,
            center = Offset(x = -width * 0.0f, y = height * 1.06f)
        )

        // Medium faint circle bottom-right
        drawCircle(
            color = Color(0xFF7D4CFF).copy(alpha = 0.08f),
            radius = width * 0.45f,
            center = Offset(x = width * 0.9f, y = height * 1.09f)
        )

        // additional small circle at top-right area
        drawCircle(
            color = Color(0xFF7D4CFF).copy(alpha = 0.08f),
            radius = width * 0.45f,
            center = Offset(x = width * 0.9f, y = -height * 0.09f)
        )
    }
}



