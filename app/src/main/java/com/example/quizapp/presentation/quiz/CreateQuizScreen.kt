package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CreateQuizScreen(onCreate: (String, Int) -> Unit) {
    var name by remember { mutableStateOf("") }
    var questionCount by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Create Quiz", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Quiz Name") })
        OutlinedTextField(value = questionCount, onValueChange = { questionCount = it }, label = { Text("Number of Questions") })
        Button(onClick = { onCreate(name, questionCount.toIntOrNull() ?: 0) }) { Text("Create") }
    }
}
