package com.example.quizapp.domain.models

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val answerIndex: Int
)