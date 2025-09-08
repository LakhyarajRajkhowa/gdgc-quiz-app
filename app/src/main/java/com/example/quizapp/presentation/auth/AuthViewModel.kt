package com.example.quizapp.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.storage.TokenManager
import com.example.quizapp.data.models.*
import com.example.quizapp.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import okhttp3.ResponseBody
import org.json.JSONObject


class AuthViewModel(
    private val repository: QuizRepository,
    private val tokenManager: TokenManager // ✅ inject TokenManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(scholarId: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                val response = repository.login(LoginRequest(scholarId, password))

                val token = tokenManager.getToken()
                if (token != null && response.user != null ) {
                    _authState.value = AuthState.Success(token)
                    Log.d("LoginTest", "Login success: token=$token, user=${response.user}")
                } else {
                    _authState.value = AuthState.Error("Login failed: missing token or user")
                }
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is HttpException -> {
                        val errorBody = e.response()?.errorBody()?.string()
                        parseLoginErrorMessage(errorBody)
                    }
                    else -> e.message ?: "Unknown error"
                }
                _authState.value = AuthState.Error(errorMessage)
                Log.d("LoginTest", "Login failed: $errorMessage")
            }
        }
    }



    fun register(username: String, scholarId: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.register(RegisterRequest(username, scholarId, password))
                val msg = response.message ?: "Registered successfully"
                _authState.value = AuthState.Message(msg)
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is HttpException -> {
                        val errorBody = e.response()?.errorBody()?.string()
                        parseErrorMessage(errorBody)
                    }
                    else -> e.message ?: "Unknown error"
                }
                _authState.value = AuthState.Error(errorMessage)
            }
        }
    }


}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error(val error: String) : AuthState()
    data class Message(val message: String) : AuthState()
}

private fun parseErrorMessage(errorBody: String?): String {
    return try {
        if (errorBody.isNullOrEmpty()) return "Unknown error"
        val json = JSONObject(errorBody)
        json.optString("error", "Unknown error")  // ✅ Looks for 'error' key
    } catch (e: Exception) {
        "Unknown error"
    }
}

private fun parseLoginErrorMessage(errorBody: String?): String {
    return try {
        if (errorBody.isNullOrEmpty()) return "Unknown error"
        val json = JSONObject(errorBody)
        json.optString("message", "Unknown error")  // ✅ Looks for 'message' key
    } catch (e: Exception) {
        "Unknown error"
    }
}
