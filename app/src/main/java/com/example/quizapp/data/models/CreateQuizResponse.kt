package com.example.quizapp.data.models

import com.google.gson.annotations.SerializedName

data class CreateQuizResponse(
    @SerializedName("id") val quizId: Int?,
    val code: String?,
    val message: String?
)

