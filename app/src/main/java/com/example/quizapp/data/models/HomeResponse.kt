package com.example.quizapp.data.models


data class HomeResponse(
    val username: String,
    val liveQuizTitle: String?,
    val progressPercent: Int,
    val ranking: Int,
    val totalScore: Int,
    val quizzesAttempted: Int,
    val coins: Int
)

data class DailyChallengeResponse(
    val challenge: String
)

data class CreateQuizResponse(
    val message: String,
    val quizId: String
)
