package com.example.quizapp.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

enum class RegisterStep {
    USERNAME, SCHOLAR_ID, PASSWORD
}

@Composable
fun RegisterScreen(
    onFinish: (username: String, scholarId: String, password: String) -> Unit
) {
    var currentStep by remember { mutableStateOf(RegisterStep.USERNAME) }
    var username by remember { mutableStateOf("") }
    var scholarId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val totalSteps = RegisterStep.values().size
    val currentStepIndex = RegisterStep.values().indexOf(currentStep) + 1
    val progress = currentStepIndex / totalSteps.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress bar at top
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        when (currentStep) {
            RegisterStep.USERNAME -> {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Enter Username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { if (username.isNotBlank()) currentStep = RegisterStep.SCHOLAR_ID },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Next")
                }
            }

            RegisterStep.SCHOLAR_ID -> {
                OutlinedTextField(
                    value = scholarId,
                    onValueChange = { scholarId = it },
                    label = { Text("Enter Scholar ID") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { if (scholarId.isNotBlank()) currentStep = RegisterStep.PASSWORD },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Next")
                }
            }

            RegisterStep.PASSWORD -> {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Create Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { if (password.isNotBlank()) onFinish(username, scholarId, password) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Finish")
                }
            }
        }
    }
}
