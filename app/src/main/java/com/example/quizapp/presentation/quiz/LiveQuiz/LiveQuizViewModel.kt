package com.example.quizapp.presentation.quiz.LiveQuiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

data class LeaderboardItem(
    val rank: Int,
    val userId: Int,
    val username: String,
    val score: Int
)



class LiveQuizViewModel(
    private val repo: QuizRepository,
    private val quizManager: LiveQuizManager,
    private val userId: Int?
) : ViewModel() {
    private val _totalQuestions = MutableStateFlow(0)
    val totalQuestions: MutableStateFlow<Int> = _totalQuestions

    private val _quizStarted = MutableStateFlow<String?>(null)
    val quizStarted: StateFlow<String?> = _quizStarted

    private val _newQuestion = MutableStateFlow<JSONObject?>(null)
    val newQuestion: StateFlow<JSONObject?> = _newQuestion

    private val _questionEnded = MutableStateFlow<JSONObject?>(null)
    val questionEnded: StateFlow<JSONObject?> = _questionEnded

    private val _quizEnded = MutableStateFlow<Boolean>(false)
    val quizEnded: StateFlow<Boolean> = _quizEnded

    private val _leaderboardState = MutableStateFlow<List<LeaderboardItem>>(emptyList())
    val leaderboardState: StateFlow<List<LeaderboardItem>> = _leaderboardState

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _totalScore = MutableStateFlow(0)
    val totalScore: StateFlow<Int> = _totalScore

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun startLiveQuizConnection(quizCode: String, userId: Int?) {
        connectToServer(quizCode, userId)
    }

    private fun connectToServer(quizCode: String, userId: Int?) {
        quizManager.connect(
            quizCode,
            userId,
            onQuizStarted = { id ->
                _quizStarted.value = id
                Log.d("LiveQuizVM", "Quiz Started: $id")
            },
            onNewQuestion = { question ->
                if (_quizStarted.value == null) {
                    _quizStarted.value = "running" // Quiz already started, late joiner
                    Log.d("LiveQuizVM", "Quiz already running, handling as late joiner.")
                }
                _newQuestion.value = JSONObject(question.toString())
                _error.value = null
                Log.d("LiveQuizVM", "New Question: $question")
            },
            onQuestionEnded = { result ->
                if (_quizStarted.value == null) {
                    _quizStarted.value = "running"
                    Log.d("LiveQuizVM", "Received questionEnded, treating quiz as running.")
                }
                _questionEnded.value = result
                Log.d("LiveQuizVM", "Question Ended: $result")
            },
            onQuizEnded = { leaderboardDataRaw ->
                if (_quizStarted.value == null) {
                    _quizStarted.value = "ended"
                    Log.d("LiveQuizVM", "Received quizEnded, treating quiz as ended.")
                }
                handleQuizEnded(leaderboardDataRaw)
                Log.d("LiveQuizVM", "Quiz Ended: $leaderboardDataRaw")
            },
            onUserJoined = { data ->
                val total = data.optString("totalQuestions", "0")
                _totalQuestions.value = total.toInt()
                Log.d("LiveQuizVM", "User Joined: $data, totalQuestions = $total")
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

    private fun handleQuizEnded(leaderboardDataRaw: Any) {
        val list = leaderboardDataRaw as List<Map<String, Any>>
        val leaderboard = list.map {
            LeaderboardItem(
                rank = (it["rank"] as Number).toInt(),
                userId = (it["userId"] as Number).toInt(),
                username = it["username"].toString(),
                score = (it["score"] as Number).toInt()
            )
        }
        _leaderboardState.value = leaderboard
        val myScore = leaderboard.find { it.userId == userId }?.score ?: 0
        _score.value = myScore
        _totalScore.value = totalQuestions.value * 20
        _quizEnded.value = true
    }




    fun joinQuiz(quizCode: String, userId: Int?) {
        quizManager.joinQuiz(quizCode, userId)
    }

    fun submitAnswer(quizCode: String, questionId: String, userId: Int?, answer: String) {
        quizManager.submitAnswer(quizCode, questionId, userId, answer)
    }

    fun startQuiz(quizCode: String) {
        viewModelScope.launch {
            repo.startQuiz(quizCode)
        }
    }

    fun disconnect() {
        quizManager.disconnect()
    }
    fun clearError() {
        _error.value = null
    }
    override fun onCleared() {
        super.onCleared()
        quizManager.disconnect()
    }
}
