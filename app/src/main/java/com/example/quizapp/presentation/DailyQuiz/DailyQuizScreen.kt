//package com.example.quizapp.presentation.DailyQuiz
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.lifecycle.viewmodel.compose.viewModel
//
//import androidx.compose.foundation.layout.*
//
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import com.example.quizapp.presentation.common.QuizQuestionCard
//import com.example.quizapp.presentation.quiz.DailyQuizViewModel
//import kotlinx.coroutines.Delay
//import kotlinx.coroutines.delay
//
//
//@Composable
//fun DailyQuizScreen(viewModel: DailyQuizViewModel = viewModel()) {
//    val state by viewModel.uiState.collectAsState()
//
//    when (state) {
//        is DailyChallengeState.Idle -> {
//            LaunchedEffect(Unit) {
//                viewModel.startChallenge()
//            }
//        }
//        is DailyChallengeState.Loading -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator()
//            }
//        }
//        is DailyChallengeState.Loaded -> {
//            val quizState = state as DailyChallengeState.Loaded
//            QuizQuestionCard(
//                quiz = quizState.quizzes[quizState.currentIndex],
//                timer = quizState.timer
//            )
//
//            // Start ticking timer
//            LaunchedEffect(quizState.currentIndex, quizState.timer) {
//                if (quizState.timer > 0) {
//                    delay(1000)
//                    viewModel.tickTimer()
//                }
//            }
//        }
//        is DailyChallengeState.Finished -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text("🎉 Challenge Finished!")
//            }
//        }
//        is DailyChallengeState.Error -> {
//            Text("Error: ${(state as DailyChallengeState.Error).message}")
//        }
//    }
//}
