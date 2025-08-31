package com.example.quizapp.data.models

data class Quiz(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)
