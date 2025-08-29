package com.example.quizapp.data.mapper

import com.example.quizapp.data.DailyQuizApi.QuizItem
import com.example.quizapp.data.models.Question

fun QuizItem.toQuestion(): Question {
    val allOptions = (incorrect_answers + correct_answer).shuffled()
    return Question(
        question = question,
        options = allOptions,
        correctIndex = allOptions.indexOf(correct_answer)
    )
}
