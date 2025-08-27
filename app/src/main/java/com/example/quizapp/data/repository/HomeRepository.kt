package com.example.quizapp.data.repository

import com.example.quizapp.data.api.ApiService
import com.example.quizapp.data.models.HomeResponse

class HomeRepository(private val apiService: ApiService) {

    suspend fun getHomeData(token: String): HomeResponse {
        return apiService.getHomeData(token) // define this endpoint in ApiService
    }

    suspend fun getDailyChallenge(): String {
        return apiService.getDailyChallenge().challenge // example field
    }
}
