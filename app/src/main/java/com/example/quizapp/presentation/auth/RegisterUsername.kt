package com.example.quizapp.presentation.auth

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun RegisterUsername(onNext: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Enter Username") })
    Button(onClick = { onNext(username) }) { Text("Next") }
}
