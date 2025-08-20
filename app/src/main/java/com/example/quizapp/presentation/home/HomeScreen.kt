package com.example.quizapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HomeScreen(
    onCreateQuiz: () -> Unit,
    onJoinQuiz: () -> Unit,
    performance: String
) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Dashboard", style = MaterialTheme.typography.headlineMedium)
        Text("Performance: $performance")
        Button(onClick = onCreateQuiz) { Text("Create Quiz") }
        Button(onClick = onJoinQuiz) { Text("Join Quiz") }
    }
}
