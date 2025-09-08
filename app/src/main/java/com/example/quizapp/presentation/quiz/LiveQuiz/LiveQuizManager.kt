package com.example.quizapp.presentation.quiz.LiveQuiz

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONArray
import org.json.JSONObject



class LiveQuizManager(
    private val baseUrl: String,
    private val token: String
) {
    private var socket: Socket? = null
    private var quizId: String? = null

    fun connect(
        quizCode: String,
        userId: Int?,
        onQuizStarted: (String) -> Unit,
        onNewQuestion: (JSONObject) -> Unit,
        onQuestionEnded: (JSONObject) -> Unit,
        onQuizEnded: (List<Map<String, Any>>) -> Unit,
        onUserJoined: (JSONObject) -> Unit,
        onAnswerReceived: (JSONObject) -> Unit,
        onError: (JSONObject) -> Unit
    ) {
        if (socket != null && socket!!.connected()) {
            Log.d("LiveQuizManager", "Already connected, skipping.")
            return
        }
        try {
            val opts = IO.Options().apply {
                extraHeaders = mapOf("Authorization" to listOf("Bearer $token"))
            }

            socket = IO.socket(baseUrl, opts)

            socket?.on(Socket.EVENT_CONNECT) {
                Log.d("LiveQuizManager", "✅ Connected to Socket.IO")

                val data = JSONObject().apply {
                    put("quizCode", quizCode)
                    put("userId", userId)
                }
                socket?.emit("joinQuiz", data)
            }

            socket?.on("quizStarted") { args ->
                val obj = args[0] as JSONObject
                quizId = obj.getString("quizId")
                onQuizStarted(obj.getString("quizId"))
                Log.d("LiveQuizManager", "🚀 Quiz Started: ${obj.getString("quizId")}")

            }

            socket?.on("newQuestion") { args ->
                val obj = args[0] as JSONObject
                onNewQuestion(obj)
                Log.d("LiveQuizManager", "📌 New Question: $obj")
            }

            socket?.on("questionEnded") { args ->
                val obj = args[0] as JSONObject
                onQuestionEnded(obj)
                Log.d("LiveQuizManager", "⏱ Question Ended: $obj")
            }

            socket?.on("quizEnded") { args ->
                // leaderboard array: [{ user_id: 1, score: 25 }, ...]
                val array = args[0] as JSONArray
                val leaderboard = mutableListOf<Map<String, Any>>()
                for (i in 0 until array.length()) {
                    val item = array.getJSONObject(i)
                    leaderboard.add(
                        mapOf(
                            "user_id" to item.getInt("user_id"),
                            "score" to item.getInt("score")
                        )
                    )
                }
                onQuizEnded(leaderboard)
            }

            socket?.on("userJoined") { args ->
                val obj = args[0] as JSONObject
                onUserJoined(obj)
                Log.d("LiveQuizManager", "👥 User Joined: $obj")
            }

            socket?.on("answerReceived") { args ->
                val obj = args[0] as JSONObject
                onAnswerReceived(obj)
                Log.d("LiveQuizManager", "📩 Answer Received: $obj")
            }

            socket?.on("error") { args ->
                val obj = args[0] as JSONObject
                onError(obj)
                Log.d("LiveQuizManager", "⚠ Error: $obj")
            }

            socket?.connect()
        } catch (e: Exception) {
            Log.e("LiveQuizManager", "❌ Socket connection error", e)
        }
    }

    fun joinQuiz(quizCode: String, userId: Int?) {
        val data = JSONObject().apply {
            put("quizCode", quizCode)
            put("userId", userId)
        }
        socket?.emit("joinQuiz", data)
        Log.d("LiveQuizManager", "func: Join Quiz")
    }

    fun submitAnswer(quizCode: String, questionId: String, userId: Int?, answer: String) {
        val data = JSONObject().apply {
            put("quizId", quizId)
            put("questionId", questionId)
            put("userId", userId)
            put("answer", answer)
        }
        socket?.emit("submitAnswer", data)
    }

    fun disconnect() {
        socket?.disconnect()
        socket?.off()
    }
}
