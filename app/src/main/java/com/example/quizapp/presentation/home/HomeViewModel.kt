package com.example.quizapp.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _scholarId = MutableStateFlow("")
    val scholarId: StateFlow<String> = _scholarId

    private val _quizStarted = MutableStateFlow(false)
    val quizStarted: StateFlow<Boolean> = _quizStarted

    fun setUserInfo(name: String, id: String) {
        _username.value = name
        _scholarId.value = id
    }

    fun startQuiz() {
        _quizStarted.value = true
    }
}
