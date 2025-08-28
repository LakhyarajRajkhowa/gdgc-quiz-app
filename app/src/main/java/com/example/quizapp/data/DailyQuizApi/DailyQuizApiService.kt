package com.example.quizapp.data.DailyQuizApi

import retrofit2.http.GET
import retrofit2.http.Query

interface DailyQuizApiService {
    @GET("api.php")
    suspend fun getQuizzes(
        @Query("amount") amount: Int = 10
    ): QuizResponse
}
