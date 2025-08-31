package com.example.quizapp.data.models

data class User(
    val username: String,
    val scholar_id: String,
    val password: String
)

data class LoginRequest(
    val scholar_id: String,
    val password: String
)

data class LoginResponse(
    val userId: String,
    val scholar_id: String,
    val username: String,
    val token: String
)
