package com.example.quizapp.data.DailyQuizApi

data class QuizResponse(
    val response_code: Int,
    val results: List<QuizItem>
)

data class QuizItem(
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)
