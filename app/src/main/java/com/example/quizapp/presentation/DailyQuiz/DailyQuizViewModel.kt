package com.example.quizapp.presentation.DailyQuiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.DailyQuizApi.QuizItem
import com.example.quizapp.data.repository.DailyQuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DailyQuizViewModel(private val dailyQuizRepository: DailyQuizRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DailyChallengeState>(DailyChallengeState.Idle)
    val uiState: StateFlow<DailyChallengeState> = _uiState

    fun startChallenge() {
        viewModelScope.launch {
            _uiState.value = DailyChallengeState.Loading
            try {
                val quizzes = dailyQuizRepository.fetchQuizzes()
                _uiState.value = DailyChallengeState.Loaded(quizzes, 0, 30)
            } catch (e: Exception) {
                _uiState.value = DailyChallengeState.Error("Failed to load quizzes")
            }
        }
    }

    fun nextQuestion() {
        val current = _uiState.value
        if (current is DailyChallengeState.Loaded) {
            val nextIndex = current.currentIndex + 1
            if (nextIndex < current.quizzes.size) {
                _uiState.value = current.copy(currentIndex = nextIndex, timer = 30)
            } else {
                _uiState.value = DailyChallengeState.Finished
            }
        }
    }

    fun tickTimer() {
        val current = _uiState.value
        if (current is DailyChallengeState.Loaded && current.timer > 0) {
            _uiState.value = current.copy(timer = current.timer - 1)
        } else if (current is DailyChallengeState.Loaded && current.timer == 0) {
            nextQuestion()
        }
    }
}

sealed class DailyChallengeState {
    object Idle : DailyChallengeState()
    object Loading : DailyChallengeState()
    data class Loaded(
        val quizzes: List<QuizItem>,
        val currentIndex: Int,
        val timer: Int
    ) : DailyChallengeState()
    object Finished : DailyChallengeState()
    data class Error(val message: String) : DailyChallengeState()
}
