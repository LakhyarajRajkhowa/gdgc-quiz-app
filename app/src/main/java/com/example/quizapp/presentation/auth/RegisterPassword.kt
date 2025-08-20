package com.example.quizapp.presentation.auth

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun RegisterPassword(onFinish: (String) -> Unit) {
    var password by remember { mutableStateOf("") }
    OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Enter Password") })
    Button(onClick = { onFinish(password) }) { Text("Finish") }
}
