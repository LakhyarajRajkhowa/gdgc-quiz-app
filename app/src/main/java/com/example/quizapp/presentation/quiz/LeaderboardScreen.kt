package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.quizapp.data.models.LeaderboardEntry

@Composable
fun LeaderboardScreen(entries: List<LeaderboardEntry>) {
    LazyColumn {
        items(entries) { entry ->
            Text("${entry.username}: ${entry.score}")
        }
    }
}
