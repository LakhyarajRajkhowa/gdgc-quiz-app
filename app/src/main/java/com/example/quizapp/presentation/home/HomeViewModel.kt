package com.example.quizapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.models.PerformanceResponse
import com.example.quizapp.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: QuizRepository) : ViewModel() {

    private val _performance = MutableStateFlow<PerformanceResponse?>(null)
    val performance: StateFlow<PerformanceResponse?> = _performance

    fun loadPerformance() {
        viewModelScope.launch {
            try {
                val data = repository.getPerformance()
                _performance.value = data
            } catch (e: Exception) {
                // handle error, maybe set _performance.value = null
            }
        }
    }
}
