package com.example.quizapp.data

import android.content.Context
import com.example.quizapp.domain.models.Question
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuestionRepository(private val context: Context) {

    fun loadQuestions(): List<Question> {
        val json = context.assets.open("questions.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<Question>>() {}.type
        return Gson().fromJson(json, type)
    }
}
