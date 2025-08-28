package com.example.quizapp.data.models

data class User(
    val username: String,
    val scholarId: String,
    val password: String
)

data class LoginRequest(
    val scholarId: String,
    val password: String
)

data class LoginResponse(
    val userId: String,
    val scholarId: String,
    val username: String,
    val token: String
)
