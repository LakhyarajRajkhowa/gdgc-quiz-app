package com.example.quizapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quizapp.data.api.ApiClient
import com.example.quizapp.presentation.auth.*
import com.example.quizapp.presentation.home.*
import com.example.quizapp.presentation.quiz.*
import com.example.quizapp.data.models.LeaderboardEntry
import com.example.quizapp.data.repository.QuizRepository

@Composable
fun AppNavHost(navController: NavHostController) {

    // ✅ Provide AuthViewModel here (shared across screens if needed)
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(QuizRepository(ApiClient.apiService))
    )


    NavHost(
        navController = navController,
        startDestination = NavRoutes.LOGIN
    ) {
        // 🔹 LOGIN
        composable(NavRoutes.LOGIN) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = { token ->
                    // Navigate to HOME if login succeeds
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.LOGIN) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(NavRoutes.REGISTER)
                }
            )
        }

        // 🔹 REGISTER
        composable(NavRoutes.REGISTER) {
            RegisterScreen { username, scholarId, password ->
                authViewModel.register(username, scholarId, password)
                navController.navigate(NavRoutes.LOGIN)
            }
        }

        // 🔹 HOME
        composable(NavRoutes.HOME) {
            HomeScreen(
                onCreateQuiz = { navController.navigate(NavRoutes.CREATE_QUIZ) },
                onJoinQuiz = { navController.navigate(NavRoutes.JOIN_QUIZ) },
                performance = "Loading..." // later connect to HomeViewModel
            )
        }

        // 🔹 CREATE QUIZ
        composable(NavRoutes.CREATE_QUIZ) {
            CreateQuizScreen(onCreate = { name, count ->
                // TODO: QuizViewModel.createQuiz()
                navController.navigate(NavRoutes.HOME)
            })
        }

        // 🔹 JOIN QUIZ
        composable(NavRoutes.JOIN_QUIZ) {
            JoinQuizScreen(onJoin = { code ->
                // TODO: QuizViewModel.joinQuiz()
                navController.navigate(NavRoutes.LIVE_QUIZ)
            })
        }

        // 🔹 LIVE QUIZ
        composable(NavRoutes.LIVE_QUIZ) {
            LiveQuizScreen(
                question = "Sample Question?",
                options = listOf("A", "B", "C", "D"),
                onAnswer = { index ->
                    // TODO: QuizViewModel.answerQuestion(index)
                    navController.navigate(NavRoutes.LEADERBOARD)
                }
            )
        }

        // 🔹 LEADERBOARD
        composable(NavRoutes.LEADERBOARD) {
            LeaderboardScreen(
                entries = listOf(
                    LeaderboardEntry("Alice", 50),
                    LeaderboardEntry("Bob", 40)
                )
            )
        }
    }
}
