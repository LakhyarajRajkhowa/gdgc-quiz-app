package com.example.quizapp.presentation.navigation

import androidx.compose.runtime.Composable
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

    // 🔹 TokenManager
    val tokenManager = remember { TokenManager(context) }
    val isLoggedIn = tokenManager.getToken() != null   // ✅ check if token exists

    // ViewModels
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

    // 🔹 Dynamic start destination
    val startDestination = if (isLoggedIn) {
        NavRoutes.HOME   // user already logged in → go directly home
    } else {
        NavRoutes.ONBOARDING1   // first time → onboarding/login
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
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
                    tokenManager.saveToken(token) // ✅ Save token
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
            RegisterScreen(
                onFinish = { username, scholarId, password ->
                    authViewModel.register(username, scholarId, password)
                    navController.navigate(NavRoutes.LOGIN)
                },
                onBackToLogin = { navController.popBackStack() }
            )
        }

        // 🔹 HOME
        composable(NavRoutes.HOME) {
            HomeScreen(
                homeViewModel = homeViewModel,
                dataStoreManager = dataStoreManager,
                onJoinQuiz = { navController.navigate(NavRoutes.JOIN_QUIZ) },
                onFabClick = { navController.navigate(NavRoutes.CREATE_QUIZ_SETUP) },
                onNavHome = {
                    navController.navigate(NavRoutes.HOME) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavLibrary = { navController.navigate(NavRoutes.LIBRARY) },
                onNavLeaderboard = { navController.navigate(NavRoutes.LEADERBOARD) },
                onNavMe = { navController.navigate(NavRoutes.PROFILE) },
                onGetStartedClick = { navController.navigate(NavRoutes.DAILY_QUIZ) }
            )
        }

        // 🔹 DAILY QUIZ
        composable(NavRoutes.DAILY_QUIZ) {
            QuizRoute(
                onBack = { navController.popBackStack() },
                onGoHome = {
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.HOME) { inclusive = true }
                    }
                },
                onViewLeaderboard = {}
            )
        }

        // 🔹 CREATE QUIZ
        composable(NavRoutes.CREATE_QUIZ_SETUP) {
            CreateQuizSetupScreen(
                onBack = { navController.popBackStack() },
                onNext = { title, numQuestions ->
                    navController.navigate("${NavRoutes.CREATE_QUIZ}/$title/$numQuestions")
                }
            )
        }

        composable(
            route = "${NavRoutes.CREATE_QUIZ}/{title}/{numQuestions}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("numQuestions") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val numQuestions = backStackEntry.arguments?.getInt("numQuestions") ?: 1

            CreateQuestionScreen(
                quizTitle = title,
                totalQuestions = numQuestions,
                onBack = { navController.popBackStack() },
                onFinishQuiz = {
                    navController.navigate(NavRoutes.HOME) {
                        popUpTo(NavRoutes.HOME) { inclusive = true }
                    }
                }
            )
        }

    }
}
