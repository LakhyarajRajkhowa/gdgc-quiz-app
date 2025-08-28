package com.example.quizapp.data.repository

import com.example.quizapp.data.DailyQuizApi.DailyQuizApiService
import com.example.quizapp.data.DailyQuizApi.QuizItem

class DailyQuizRepository(private val dailyQuizApiService: DailyQuizApiService) {
    suspend fun fetchQuizzes(amount: Int = 10): List<QuizItem> {
        val response = dailyQuizApiService.getQuizzes(amount)
        return response.results
    }
}
