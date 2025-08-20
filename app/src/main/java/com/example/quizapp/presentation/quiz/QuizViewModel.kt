package com.example.quizapp.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.api.WebSocketManager
import com.example.quizapp.data.models.*
import com.example.quizapp.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: QuizRepository,
    private val wsManager: WebSocketManager
) : ViewModel() {

    private val _quizState = MutableStateFlow<QuizState>(QuizState.Idle)
    val quizState: StateFlow<QuizState> = _quizState

    fun createQuiz(name: String, questions: List<Question>) {
        viewModelScope.launch {
            try {
                _quizState.value = QuizState.Loading
                val response = repository.createQuiz(Quiz(name = name, questions = questions))
                _quizState.value = QuizState.Created(response.message)
            } catch (e: Exception) {
                _quizState.value = QuizState.Error("Error creating quiz: ${e.message}")
            }
        }
    }

    fun joinQuiz(code: String) {
        viewModelScope.launch {
            try {
                _quizState.value = QuizState.Loading
                repository.joinQuiz(JoinQuizRequest(code))
                val quiz = repository.getQuiz(code)
                _quizState.value = QuizState.Joined(quiz)
            } catch (e: Exception) {
                _quizState.value = QuizState.Error("Error joining quiz: ${e.message}")
            }
        }
    }

    fun startListeningToQuiz() {
        wsManager.connect()
        // Here you’d parse incoming WebSocket messages & update _quizState
    }

    fun answerQuestion(index: Int) {
        wsManager.send("ANSWER:$index")
    }
}

sealed class QuizState {
    object Idle : QuizState()
    object Loading : QuizState()
    data class Success(val score: Int) : QuizState()
    data class Error(val message: String) : QuizState()
    data class Message(val message: String) : QuizState()
    data class Created(val message: String) : QuizState()

    // Add this ↓
    data class Joined(val quiz: Quiz) : QuizState()
}
