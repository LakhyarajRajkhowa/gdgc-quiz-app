package com.example.quizapp.data.models

data class Question(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)

data class CreateQuizRequest(
    val title: String,
    val questions: List<QuestionRequest>
)

data class QuestionRequest(
    val text: String,
    val options: List<String>,
    val correct: String,
    val time: Int
)
