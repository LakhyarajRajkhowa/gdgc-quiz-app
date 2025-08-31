package com.example.quizapp.data.api

import android.content.Context
import com.example.quizapp.data.storage.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://quiz-app-backend-7m74.onrender.com/"

    // 🔹 This will be initialized once with context
    private var apiService: ApiService? = null

    fun init(context: Context) {
        if (apiService == null) {
            val tokenManager = TokenManager(context)

            // Logging interceptor
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                // Save token from Set-Cookie on login
                .addInterceptor { chain ->
                    val response = chain.proceed(chain.request())
                    val headers = response.headers("Set-Cookie")
                    headers.forEach { header ->
                        if (header.startsWith("token=")) {
                            val token = header.substringAfter("token=").substringBefore(";")
                            tokenManager.saveToken(token)
                        }
                    }
                    response
                }
                // Attach token to all requests
                .addInterceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                    tokenManager.getToken()?.let { token ->
                        requestBuilder.addHeader("Authorization", "Bearer $token")
                    }
                    chain.proceed(requestBuilder.build())
                }
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }
    }

    // 🔹 Global access point
    fun getService(): ApiService {
        return apiService
            ?: throw IllegalStateException("ApiClient is not initialized. Call ApiClient.init(context) first.")
    }
}
