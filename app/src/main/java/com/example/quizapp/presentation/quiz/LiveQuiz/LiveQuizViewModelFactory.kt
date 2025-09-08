package com.example.quizapp.presentation.quiz.LiveQuiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.data.repository.QuizRepository

class LiveQuizViewModelFactory(
    private val repo: QuizRepository,
    private val quizManager: LiveQuizManager,
    private val userId: Int?
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LiveQuizViewModel::class.java)) {
            return LiveQuizViewModel(repo, quizManager, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
