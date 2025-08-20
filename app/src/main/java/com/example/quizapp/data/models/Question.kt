package com.example.quizapp.data.models

data class Question(
    val id: Int? = null,
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)
