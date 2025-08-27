package com.example.quizapp.presentation.home

import HomeUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    fun loadHomeData(token: String) {
        viewModelScope.launch {
            try {
                _uiState.value = HomeUiState(isLoading = true)

                val response = repository.getHomeData(token)
                val challenge = repository.getDailyChallenge()

                _uiState.value = HomeUiState(
                    username = response.username,
                    liveQuizTitle = response.liveQuizTitle,
                    progressPercent = response.progressPercent,
                    ranking = response.ranking,
                    totalScore = response.totalScore,
                    quizzesAttempted = response.quizzesAttempted,
                    coins = response.coins,
                    dailyChallenge = challenge,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = HomeUiState(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
}
