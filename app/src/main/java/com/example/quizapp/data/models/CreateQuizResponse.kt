package com.example.quizapp.data.models

data class CreateQuizResponse(
    val quizId: Int,
    val message: String,
    val code: String? = null // ✅ optional for now, backend will add later
)
