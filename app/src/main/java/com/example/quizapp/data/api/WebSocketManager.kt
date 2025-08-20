package com.example.quizapp.data.api

import okhttp3.*
import okio.ByteString

class WebSocketManager(private val url: String) : WebSocketListener() {
    private var webSocket: WebSocket? = null

    fun connect() {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, this)
    }

    fun send(message: String) {
        webSocket?.send(message)
    }

    fun close() {
        webSocket?.close(1000, "Closing")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        println("WebSocket Opened")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println("Message Received: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        println("Binary Received: ${bytes.hex()}")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        println("Closing: $reason")
    }
}
