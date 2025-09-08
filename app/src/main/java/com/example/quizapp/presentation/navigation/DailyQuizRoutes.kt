package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizapp.data.repository.DailyQuizRepository
import com.example.quizapp.data.DailyQuizApi.DailyQuizApiClient
import com.example.quizapp.presentation.common.QuizLoadingScreen
import com.example.quizapp.presentation.quiz.DailyQuiz.DailyQuizScreen
import com.example.quizapp.presentation.quiz.DailyQuiz.DailyQuizStartScreen
import com.example.quizapp.presentation.quiz.DailyQuiz.DailyQuizViewModel
import com.example.quizapp.presentation.quiz.DailyQuiz.DailyQuizViewModelFactory


@Composable
fun QuizRoute(
    onBack: () -> Unit,
    onGoHome: () -> Unit,
    onViewLeaderboard: () -> Unit
) {
    val repository = remember { DailyQuizRepository(DailyQuizApiClient.dailyQuizApiService) }

    val viewModel: DailyQuizViewModel = viewModel(
        factory = DailyQuizViewModelFactory(repository)
    )

    var quizFinished by remember { mutableStateOf(false) }
    var finalScore by remember { mutableStateOf(0) }
    var totalQuestions by remember { mutableStateOf(0) }
    var hasStarted by remember { mutableStateOf(false) }

    when {
        viewModel.isLoading.value -> {
            QuizLoadingScreen()
        }
        viewModel.errorMessage.value != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${viewModel.errorMessage.value}")
            }
        }
        !hasStarted -> {
            // ✅ Show start screen first
            DailyQuizStartScreen(
                onBegin = { hasStarted = true },
                onBack = onBack
            )
        }
        quizFinished -> {
            QuizCompletedScreen(
                score = finalScore,
                totalScore = totalQuestions,
                onViewLeaderboard = onViewLeaderboard,
                onGoHome = onGoHome
            )
        }
        else -> {
            DailyQuizScreen(
                questions = viewModel.quizQuestions.value,
                onBack = onBack,
                onFinish = { score, total ->
                    finalScore = score
                    totalQuestions = total
                    quizFinished = true
                }
            )
        }
    }
}
