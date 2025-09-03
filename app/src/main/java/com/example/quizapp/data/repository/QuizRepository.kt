package com.example.quizapp.data.repository

import com.example.quizapp.data.api.ApiService
import com.example.quizapp.data.models.*

class QuizRepository(private val api: ApiService) {

    // 🔹 Register new user
    suspend fun register(request: RegisterRequest) = api.register(request)

    // 🔹 Login user
    suspend fun login(credentials: LoginRequest) = api.login(credentials)

    // Future APIs:
    // suspend fun createQuiz(quiz: Quiz) = api.createQuiz(quiz)
    // suspend fun joinQuiz(request: JoinQuizRequest) = api.joinQuiz(request)
    // suspend fun getQuiz(code: String) = api.getQuiz(code)
    // suspend fun getPerformance() = api.getPerformance()
}
