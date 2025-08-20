package com.example.quizapp.data.models

data class Quiz(
    val id: Int? = null,
    val name: String,
    val code: String? = null,
    val questions: List<Question> = emptyList()
)

data class JoinQuizRequest(
    val code: String
)
