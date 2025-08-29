package com.example.quizapp.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.data.repository.DailyQuizRepository

class DailyQuizViewModelFactory(
    private val repository: DailyQuizRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyQuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DailyQuizViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
