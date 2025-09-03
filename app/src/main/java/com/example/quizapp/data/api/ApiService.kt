package com.example.quizapp.data.api

import com.example.quizapp.data.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    // 🔹 Authentication
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse


    @POST("api/auth/login")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse



    // 🔹 Quiz APIs

    @POST("api/quiz/create")
    suspend fun createQuiz(
        @Body quiz: CreateQuizRequest
    ): retrofit2.Response<CreateQuizResponse>

   // @POST("api/quiz/join")
    //suspend fun joinQuiz(@Body request: JoinQuizRequest): ApiResponse // Join quiz by code

  //  @GET("quiz/{code}")
  //  suspend fun getQuiz(@Path("code") code: String): Quiz   // Get quiz by code


    // 🔹 User data
    @GET("user/performance")
    suspend fun getPerformance(@Header("Authorization") token: String): PerformanceResponse

    @GET("home")
    suspend fun getHomeData(@Header("Authorization") token: String): HomeResponse

    @GET("dailyChallenge")
    suspend fun getDailyChallenge(): DailyChallengeResponse
}
