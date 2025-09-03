package com.example.quizapp.data.models

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val username: String,
    val scholar_id: String,
    val password: String
)

// What backend returns inside "user"
data class User(
    val id: Int?,
    val username: String?,
    @SerializedName("sch_id") val scholarId: String?,
    val created_at: String?,
    val updated_at: String?
)

// Login request you already have (keep as-is)
data class LoginRequest(
    @SerializedName("scholar_id") val scholarId: String,
    val password: String
)

// Wraps the "user" object
data class LoginResponse(
    val user: User? // since backend returns {"user": {...}}
)
