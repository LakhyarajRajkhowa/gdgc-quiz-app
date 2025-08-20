package com.example.quizapp.presentation.auth

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun RegisterScholarID(onNext: (String) -> Unit) {
    var scholarId by remember { mutableStateOf("") }
    OutlinedTextField(value = scholarId, onValueChange = { scholarId = it }, label = { Text("Enter Scholar ID") })
    Button(onClick = { onNext(scholarId) }) { Text("Next") }
}
