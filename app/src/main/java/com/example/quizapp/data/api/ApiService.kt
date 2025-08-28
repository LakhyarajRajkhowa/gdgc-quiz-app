package com.example.quizapp.data.api

import com.example.quizapp.data.models.*
import retrofit2.http.*

interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body user: User): ApiResponse

    @POST("auth/login")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

   // @POST("quiz/create")
    //suspend fun createQuiz(@Body quiz: Quiz): ApiResponse

    //@POST("quiz/join")
   // suspend fun joinQuiz(@Body request: JoinQuizRequest): ApiResponse

   // @GET("quiz/{code}")
   // suspend fun getQuiz(@Path("code") code: String): Quiz

    @GET("user/performance")
    suspend fun getPerformance(): PerformanceResponse

    @GET("home")
    suspend fun getHomeData(@Header("Authorization") token: String): HomeResponse

    @GET("dailyChallenge")
    suspend fun getDailyChallenge(): DailyChallengeResponse

}
