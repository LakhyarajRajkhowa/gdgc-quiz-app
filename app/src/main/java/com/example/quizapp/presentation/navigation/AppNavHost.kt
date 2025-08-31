package com.example.quizapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quizapp.data.DailyQuizApi.DailyQuizApiClient
import com.example.quizapp.data.DataStoreManager
import com.example.quizapp.data.api.ApiClient
import com.example.quizapp.presentation.auth.*
import com.example.quizapp.presentation.home.*
import com.example.quizapp.presentation.quiz.*
import com.example.quizapp.data.models.LeaderboardEntry
import com.example.quizapp.data.repository.DailyQuizRepository
import com.example.quizapp.data.repository.HomeRepository
import com.example.quizapp.data.repository.QuizRepository
import com.example.quizapp.R
import com.example.quizapp.data.storage.TokenManager
import com.example.quizapp.presentation.quiz.DailyQuiz.DailyQuizViewModel
import com.example.quizapp.presentation.quiz.DailyQuiz.DailyQuizViewModelFactory


@Composable
fun AppNavHost(navController: NavHostController) {

    val context = LocalContext.current

    // 🔹 Create TokenManager
    val tokenManager = remember { TokenManager(context) }

    // 🔹 AuthViewModel with TokenManager
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(
            QuizRepository(ApiClient.getService()),
            tokenManager
        )
    )

    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(HomeRepository(ApiClient.getService()))
    )

    val dailyQuizViewModel: DailyQuizViewModel = viewModel(
        factory = DailyQuizViewModelFactory(
            DailyQuizRepository(DailyQuizApiClient.dailyQuizApiService)
        )
    )

    val dataStoreManager = remember { DataStoreManager(context) }


    NavHost(
        navController = navController,
        startDestination = NavRoutes.ONBOARDING1
    ) {
        composable(NavRoutes.ONBOARDING1) {
            OnboardingScreen(
                imageRes = R.drawable.onboarding1,
                title = "Let’s get started!",
                button1Text = "Next",
                onButton1Click = { navController.navigate(NavRoutes.ONBOARDING2) },
                button2Text = "Login",
                onButton2Click = { navController.navigate(NavRoutes.LOGIN) }
            )
        }

        composable(NavRoutes.ONBOARDING2) {
            OnboardingScreen(
                imageRes = R.drawable.onboarding2,
                title = "Stay motivated!",
                description = "Track your progress and keep improving...",
                button1Text = "Next",
                onButton1Click = { navController.navigate(NavRoutes.ONBOARDING3) }
            )
        }

        composable(NavRoutes.ONBOARDING3) {
            OnboardingScreen(
                imageRes = R.drawable.onboarding3,
                title = "Challenge yourself!",
                description = "Compete in quizzes and test your knowledge.",
                button1Text = "Next",
                onButton1Click = { navController.navigate(NavRoutes.ONBOARDING4) }
            )
        }

        composable(NavRoutes.ONBOARDING4) {
            OnboardingScreen(
                imageRes = R.drawable.onboarding4,
                title = "Welcome!",
                description = "Let’s begin your journey with us.",
                button1Text = "Get Started!",
                onButton1Click = {
                    // Replace onboarding with login (so users can’t go back)
                    navController.navigate(NavRoutes.LOGIN) {
                        popUpTo(NavRoutes.ONBOARDING1) { inclusive = true }
                    }
                }
            )
        }
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
           RegisterScreen (  onFinish = {username, scholarId, password ->
                authViewModel.register(username, scholarId, password)
                navController.navigate(NavRoutes.LOGIN)},
               onBackToLogin = { navController.popBackStack() }
            )
        }


        composable(NavRoutes.HOME) {

            HomeScreen(
                homeViewModel = homeViewModel,
                dataStoreManager = dataStoreManager,
                // 🔹 Handle Join Quiz
                onJoinQuiz = {
                    navController.navigate(NavRoutes.JOIN_QUIZ)
                },

                // 🔹 Floating Action Button (create quiz)
                onFabClick = {
                    navController.navigate(NavRoutes.CREATE_QUIZ)
                },

                // 🔹 Bottom navigation tabs
                onNavHome = {
                    navController.navigate(NavRoutes.HOME) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavLibrary = {
                    navController.navigate(NavRoutes.LIBRARY)
                },
                onNavLeaderboard = {
                    navController.navigate(NavRoutes.LEADERBOARD)
                },
                onNavMe = {
                    navController.navigate(NavRoutes.PROFILE)
                },
                onGetStartedClick = {
                    navController.navigate(NavRoutes.DAILY_QUIZ)
                }
            )
        }

        // DAILY QUIZ
        // DAILY QUIZ
        composable(NavRoutes.DAILY_QUIZ) {
            QuizRoute(
                onBack = { navController.popBackStack() },
                onGoHome = { navController.navigate(NavRoutes.HOME) {
                    popUpTo(NavRoutes.HOME) { inclusive = true }
                }
                },
                onViewLeaderboard = {}
            )
        }

//        composable(
//            route = "${NavRoutes.TOTAL_SCORE}/{score}/{total}",
//            arguments = listOf(
//                navArgument("score") { type = NavType.IntType },
//                navArgument("total") { type = NavType.IntType }
//            )
//        ) { backStackEntry ->
//            val score = backStackEntry.arguments?.getInt("score") ?: 0
//            val total = backStackEntry.arguments?.getInt("total") ?: 0
//
//            QuizCompletedScreen(
//                score = score,
//                totalScore = total,
//                onViewLeaderboard = {},
//                onGoHome = {
//                    navController.navigate(NavRoutes.HOME) {
//                        popUpTo(NavRoutes.HOME) { inclusive = true }
//                    }
//                }
//            )
//        }





        // 🔹 CREATE QUIZ
        composable(NavRoutes.CREATE_QUIZ) {
            CreateQuestionScreen(
                onBack = { navController.popBackStack() },
                onFinishQuiz = {
                    // Navigate back to Home after finishing quiz
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.HOME) { inclusive = true }
                    }
                },
                onNext = {
                    // TODO: Save next question logic (API or local list)
                    // For now, just stay on the same screen
                }
            )
        }



        // 🔹 JOIN QUIZ
//        composable(NavRoutes.JOIN_QUIZ) {
//            JoinQuizScreen(onJoin = { code ->
//                // TODO: QuizViewModel.joinQuiz()
//                navController.navigate(NavRoutes.LIVE_QUIZ)
//            })
//        }

        // 🔹 LIVE QUIZ
//        composable(NavRoutes.LIVE_QUIZ) {
//            LiveQuizScreen(
//                question = "Sample Question?",
//                options = listOf("A", "B", "C", "D"),
//                onAnswer = { index ->
//                    // TODO: QuizViewModel.answerQuestion(index)
//                    navController.navigate(NavRoutes.LEADERBOARD)
//                }
//            )
//        }

        // 🔹 LEADERBOARD
//        composable(NavRoutes.LEADERBOARD) {
//            LeaderboardScreen(
//                entries = listOf(
//                    LeaderboardEntry("Alice", 50),
//                    LeaderboardEntry("Bob", 40)
//                )
//            )
//        }
    }
}
