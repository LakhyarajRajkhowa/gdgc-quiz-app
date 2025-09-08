package com.example.quizapp.data.models


data class HomeResponse(
    val username: String,
    val liveQuizTitle: String?,
    val progressPercent: Int,
    val ranking: Int,
    val totalScore: Int,
    val quizzesAttempted: Int,
    val coins: Int,
    val isAdmin: Boolean
)

data class DailyChallengeResponse(
    val challenge: String
)

//{
//    "username": lakhyaraj,
//    "scholar_id": 2412031,
//    "accuracy_percent": 69,
//    "ranking": 5,
//    "quizzes_attempted": 10,
//
//}