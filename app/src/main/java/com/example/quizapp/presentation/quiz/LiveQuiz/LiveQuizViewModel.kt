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
        quizManager.connect(
            quizId,
            userId,
            onQuizStarted = { id ->
                _quizStarted.value = id
                Log.d("LiveQuizVM", "Quiz Started: $id")
            },
            onNewQuestion = { question ->
                // Clone the JSONObject to create a new instance
                _newQuestion.value = JSONObject(question.toString())
                _error.value = null  // Reset error for new question

                Log.d("LiveQuizVM", "New Question: $question")
            },
            onQuestionEnded = { result ->
                _questionEnded.value = result
                Log.d("LiveQuizVM", "Question Ended: $result")
            },
            onQuizEnded = { result ->
                _quizEnded.value = result
                Log.d("LiveQuizVM", "Quiz Ended: $result")
            },
            onUserJoined = { data ->
                Log.d("LiveQuizVM", "User Joined: $data")
            },
            onAnswerReceived = { data ->
                Log.d("LiveQuizVM", "Answer Received: $data")
            },
            onError = { data ->
                _error.value = data.optString("message")
                Log.e("LiveQuizVM", "Error: ${data.optString("message")}")
            }
        )
    }

    fun joinQuiz(quizId: String, userId: Int?) {
        quizManager.joinQuiz(quizId, userId)
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

    fun disconnect(){
        quizManager.disconnect()
    }
    fun startLiveQuizConnection(quizId: String, userId: Int?) {
        connectToServer(quizId, userId)
        joinQuiz(quizId, userId)
    }


}

