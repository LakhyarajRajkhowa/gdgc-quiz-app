package com.example.quizapp.presentation.quiz.DailyQuiz

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.mapper.toQuestion
import com.example.quizapp.data.models.Question
import com.example.quizapp.data.repository.DailyQuizRepository
import kotlinx.coroutines.launch

class DailyQuizViewModel(private val repository: DailyQuizRepository) : ViewModel() {

    var quizQuestions = mutableStateOf<List<Question>>(emptyList())
        private set

    var isLoading = mutableStateOf(true)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    init {
        loadQuiz()
    }

    private fun loadQuiz(amount: Int = 10) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val items = repository.fetchQuizzes(amount)
                quizQuestions.value = items.map { it.toQuestion() }
                errorMessage.value = null
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }
}