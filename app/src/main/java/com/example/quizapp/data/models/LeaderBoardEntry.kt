package com.example.quizapp.data.models

data class LeaderboardEntry(
    val username: String,
    val score: Int
)

data class PerformanceResponse(
    val totalAnswered: Int,
    val correctAnswers: Int,
    val accuracy: Double
)
