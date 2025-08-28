package com.example.quizapp.data.DailyQuizApi


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DailyQuizApiClient {

    val dailyQuizApiService: DailyQuizApiService by lazy{
        // 1️⃣ Create a logging interceptor
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs request and response bodies
        }

        // 2️⃣ Create OkHttp client with logging
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("https://opentdb.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DailyQuizApiService::class.java)

    }
}
