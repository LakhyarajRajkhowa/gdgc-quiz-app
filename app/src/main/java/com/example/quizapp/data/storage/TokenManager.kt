package com.example.quizapp.data.storage


import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import org.json.JSONObject

class TokenManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("quiz_app_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("jwt_token", null)
    }

    fun clearToken() {
        prefs.edit().remove("jwt_token").apply()
    }
}



fun extractUserIdFromToken(token: String): Int? {
    return try {
        val parts = token.split(".")
        if (parts.size < 2) return null
        val payload = parts[1]

        // Decode Base64 URL-safe without padding
        val decodedBytes = Base64.decode(payload, Base64.URL_SAFE or Base64.NO_WRAP)
        val decodedString = String(decodedBytes, Charsets.UTF_8)

        // Parse JSON
        val json = JSONObject(decodedString)
        json.getInt("id")  // return userId
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun extractUsernameFromToken(token: String): String? {
    return try {
        val parts = token.split(".")
        if (parts.size < 2) return null
        val payload = parts[1]

        // Decode Base64 URL-safe without padding
        val decodedBytes = Base64.decode(payload, Base64.URL_SAFE or Base64.NO_WRAP)
        val decodedString = String(decodedBytes, Charsets.UTF_8)

        // Parse JSON
        val json = JSONObject(decodedString)
        json.optString("username", null)  // return username or null if not present
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun extractIsAdminFromToken(token: String): Boolean? {
    return try {
        val parts = token.split(".")
        if (parts.size < 2) return null
        val payload = parts[1]

        val decodedBytes = Base64.decode(payload, Base64.URL_SAFE or Base64.NO_WRAP)
        val decodedString = String(decodedBytes, Charsets.UTF_8)

        val json = JSONObject(decodedString)
        if (json.has("isAdmin")) json.getBoolean("isAdmin") else null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}