package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun LiveQuizScreen(question: String, options: List<String>, onAnswer: (Int) -> Unit) {
    Column {
        Text("Question: $question")
        options.forEachIndexed { index, option ->
            Button(onClick = { onAnswer(index) }) { Text(option) }
        }
    }
}
