package com.example.quizapp.presentation.quiz

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun JoinQuizScreen(onJoin: (String) -> Unit) {
    var code by remember { mutableStateOf("") }
    OutlinedTextField(value = code, onValueChange = { code = it }, label = { Text("Enter Quiz Code") })
    Button(onClick = { onJoin(code) }) { Text("Join Quiz") }
}
