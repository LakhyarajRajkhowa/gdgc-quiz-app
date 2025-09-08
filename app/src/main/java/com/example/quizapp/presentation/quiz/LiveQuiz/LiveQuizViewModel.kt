package com.example.quizapp.presentation.quiz.LiveQuiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repository.QuizRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject

class LiveQuizViewModel(
    private val repo: QuizRepository,
    private val quizManager: LiveQuizManager
) : ViewModel() {

    private val _quizStarted = MutableStateFlow<String?>(null)
    val quizStarted: StateFlow<String?> = _quizStarted

    private val _newQuestion = MutableStateFlow<JSONObject?>(null)
    val newQuestion: StateFlow<JSONObject?> = _newQuestion

    private val _questionEnded = MutableStateFlow<JSONObject?>(null)
    val questionEnded: StateFlow<JSONObject?> = _questionEnded

    private val _quizEnded = MutableStateFlow<JSONObject?>(null)
    val quizEnded: StateFlow<JSONObject?> = _quizEnded

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun connectToServer(quizId: String, userId: Int?) {
        quizManager.connect(quizId, userId)
    }

    fun joinQuiz(quizId: String, userId: Int?) {
        quizManager.joinQuiz(quizId, userId)

        quizManager.listenQuizEvents(
            onQuizStarted = { id ->
                Log.d("LiveQuizVM", "Quiz started: $id")
                _quizStarted.value = id
            },
            onNewQuestion = { q ->
                Log.d("LiveQuizVM", "New question: $q")
                _newQuestion.value = q
            },
            onQuestionEnded = { r ->
                Log.d("LiveQuizVM", "Question ended: ${r.toString()}")
                viewModelScope.launch {
                    kotlinx.coroutines.delay(500)  // Delay 500ms to simulate processing time
                    _questionEnded.value = r
                }
            },
            onQuizEnded = { lb ->
                Log.d("LiveQuizVM", "Quiz ended: $lb")
                _quizEnded.value = lb
            },
            onUserJoined = { data ->
                Log.d("LiveQuizVM", "User joined: $data")
            },
            onAnswerReceived = { data ->
                Log.d("LiveQuizVM", "Answer received: $data")
            },
            onError = { err ->
                Log.e("LiveQuizVM", "Error: ${err.getString("message")}")
            }
        )

    }

    fun submitAnswer(quizId: String, questionId: String, userId: Int?, answer: String) {
        quizManager.submitAnswer(quizId, questionId, userId, answer)
    }

    fun startQuiz(quizId: String) {
        viewModelScope.launch {
            repo.startQuiz(quizId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        quizManager.disconnect()
    }
}