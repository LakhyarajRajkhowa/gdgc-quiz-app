package com.example.quizapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "http://10.0.2.2:8080/" // For Android emulator talking to local Node.js

    val apiService: ApiService by lazy {

        // 1️⃣ Create a logging interceptor
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Logs request and response bodies
        }

        // 2️⃣ Create OkHttp client with logging
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // 3️⃣ Build Retrofit with logging client
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // <-- important: attach the logging client
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
