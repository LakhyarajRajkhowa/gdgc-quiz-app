package com.example.quizapp.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.models.*
import com.example.quizapp.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: QuizRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(scholarId: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                val response = repository.login(LoginRequest(scholarId, password))
                _authState.value = AuthState.Success(response.token)
                Log.d("LoginTest", "Login success: $response")
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Login failed: ${e.message}")
            }
        }
    }

    fun register(username: String, scholarId: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                val response = repository.register(User(username, scholarId, password))
                _authState.value = AuthState.Message(response.message)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Register failed: ${e.message}")
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

