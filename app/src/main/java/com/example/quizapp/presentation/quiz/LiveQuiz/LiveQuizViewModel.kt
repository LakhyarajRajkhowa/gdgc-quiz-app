package com.example.quizapp.presentation.quiz.LiveQuiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

data class LeaderboardItem(
    val userId: Int,
    val score: Int
)

class LiveQuizViewModel(
    private val repo: QuizRepository,
    private val quizManager: LiveQuizManager,
    private val userId: Int? // Store current user id
) : ViewModel() {

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
        joinQuiz(quizCode, userId)
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
                _newQuestion.value = JSONObject(question.toString())
                _error.value = null
                Log.d("LiveQuizVM", "New Question: $question")
            },
            onQuestionEnded = { result ->
                _questionEnded.value = result
                Log.d("LiveQuizVM", "Question Ended: $result")
            },
            onQuizEnded = { leaderboardDataRaw ->
                handleQuizEnded(leaderboardDataRaw)
                Log.d("LiveQuizVM", "Quiz Ended: $leaderboardDataRaw")
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

    private fun handleQuizEnded(leaderboardDataRaw: Any) {
        // Convert raw JSON array to list of LeaderboardItem
        val leaderboard = (leaderboardDataRaw as List<Map<String, Any>>).map {
            LeaderboardItem(
                userId = (it["user_id"] as Number).toInt(),
                score = (it["score"] as Number).toInt()
            )

        }

        // Update leaderboard state
        _leaderboardState.value = leaderboard

        // Set current user's score
        val myScore = leaderboard.find { it.userId == userId }?.score ?: 0
        _score.value = myScore

        // Optional: calculate total possible score
        _totalScore.value = leaderboard.sumOf { it.score }

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

    override fun onCleared() {
        super.onCleared()
        quizManager.disconnect()
    }
}
