package com.example.quizapp.presentation.quiz.LiveQuiz

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class LiveQuizManager(
    private val baseUrl: String,
    private val token: String
) {
    private var socket: Socket? = null

    fun joinQuiz(quizId: String, userId: Int?) {
        val data = JSONObject().apply {
            put("quizId", quizId)
            put("userId", userId)
        }
        socket?.emit("joinQuiz", data)
    }

    fun listenQuizEvents(
        onQuizStarted: (String) -> Unit,
        onNewQuestion: (JSONObject) -> Unit,
        onQuestionEnded: (JSONObject) -> Unit,
        onQuizEnded: (JSONObject) -> Unit,
        onUserJoined: (JSONObject) -> Unit,          // Add this
        onAnswerReceived: (JSONObject) -> Unit,      // Add this
        onError: (JSONObject) -> Unit                // Add this
    ) {

        socket?.on("quizStarted") { args ->
            val obj = args[0] as JSONObject
            onQuizStarted(obj.getString("quizId"))
        }

        socket?.on("newQuestion") { args ->
            val obj = args[0] as JSONObject
            onNewQuestion(obj)
        }

        socket?.on("questionEnded") { args ->
            val obj = args[0] as JSONObject
            onQuestionEnded(obj)
        }

        socket?.on("quizEnded") { args ->
            val obj = args[0] as JSONObject
            onQuizEnded(obj)
        }

        // ✅ Handle new events below
        socket?.on("userJoined") { args ->
            val obj = args[0] as JSONObject
            onUserJoined(obj)
        }

        socket?.on("answerReceived") { args ->
            val obj = args[0] as JSONObject
            onAnswerReceived(obj)
        }

        socket?.on("error") { args ->
            val obj = args[0] as JSONObject
            onError(obj)
        }
    }


    fun connect(quizCode: String, userId: Int?) {
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
                    put("quizId", quizCode)
                    put("userId", userId)
                }
                socket?.emit("joinQuiz", data)
            }


            socket?.on("quizStarted") { args ->
                Log.d("LiveQuizManager", "🚀 Quiz Started: ${args[0]}")
            }

            socket?.on("newQuestion") { args ->
                val obj = args[0] as JSONObject
                Log.d("LiveQuizManager", "📌 New Question: ${obj.toString()}")
            }

            socket?.on("questionEnded") { args ->
                val obj = args[0] as JSONObject
                Log.d("LiveQuizManager", "⏱ Question Ended: ${obj.toString()}")
            }

            socket?.on("quizEnded") { args ->
                val obj = args[0] as JSONObject
                Log.d("LiveQuizManager", "🏆 Quiz Ended Leaderboard: ${obj.toString()}")
            }

            socket?.connect()
        } catch (e: Exception) {
            Log.e("LiveQuizManager", "❌ Socket connection error", e)
        }
    }

    fun submitAnswer(quizId: String, questionId: String, userId: Int?, answer: String) {
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


