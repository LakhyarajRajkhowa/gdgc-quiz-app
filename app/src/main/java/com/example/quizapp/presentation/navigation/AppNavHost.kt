package com.example.quizapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quizapp.presentation.auth.*
import com.example.quizapp.presentation.home.*
import com.example.quizapp.presentation.quiz.*
import com.example.quizapp.data.models.LeaderboardEntry

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.LOGIN
    ) {
        composable(NavRoutes.LOGIN) {
            LoginScreen(
                onLogin = { username, password ->
                    // TODO: Call AuthViewModel.login()
                    navController.navigate(NavRoutes.HOME)
                },
                onRegisterClick = { navController.navigate(NavRoutes.REGISTER) }
            )
        }

        composable(NavRoutes.REGISTER) {
            RegisterScreen { username, scholarId, password ->
                // TODO: Call AuthViewModel.register(username, scholarId, password)
                navController.navigate(NavRoutes.LOGIN)
            }
        }

        composable(NavRoutes.HOME) {
            HomeScreen(
                onCreateQuiz = { navController.navigate(NavRoutes.CREATE_QUIZ) },
                onJoinQuiz = { navController.navigate(NavRoutes.JOIN_QUIZ) },
                performance = "Loading..." // Hook with HomeViewModel
            )
        }

        composable(NavRoutes.CREATE_QUIZ) {
            CreateQuizScreen(onCreate = { name, count ->
                // TODO: QuizViewModel.createQuiz()
                navController.navigate(NavRoutes.HOME)
            })
        }

        composable(NavRoutes.JOIN_QUIZ) {
            JoinQuizScreen(onJoin = { code ->
                // TODO: QuizViewModel.joinQuiz()
                navController.navigate(NavRoutes.LIVE_QUIZ)
            })
        }

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
