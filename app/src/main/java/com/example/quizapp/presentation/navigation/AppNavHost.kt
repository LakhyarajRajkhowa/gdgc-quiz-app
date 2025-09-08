package com.example.quizapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.quizapp.presentation.common.ComingSoonScreen
import com.example.quizapp.presentation.home.components.JoinQuizDialog
import com.example.quizapp.presentation.quiz.DailyQuiz.DailyQuizViewModel
import com.example.quizapp.presentation.quiz.DailyQuiz.DailyQuizViewModelFactory
import com.example.quizapp.presentation.quiz.LiveQuiz.LiveQuizManager
import com.example.quizapp.presentation.quiz.LiveQuiz.LiveQuizViewModel
import com.example.quizapp.presentation.quiz.LiveQuiz.LiveQuizViewModelFactory
import android.util.Log
import com.example.quizapp.data.storage.extractUserIdFromToken
import com.example.quizapp.presentation.quiz.LiveQuiz.LiveQuizSimpleScreen

@Composable
fun AppNavHost(navController: NavHostController) {

    val context = LocalContext.current

    // 🔹 TokenManager
    val tokenManager = remember { TokenManager(context) }
    val isLoggedIn = tokenManager.getToken() != null   // ✅ check if token exists
    val token = tokenManager.getToken() ?: ""

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

    val liveQuizViewModel: LiveQuizViewModel = viewModel(
        factory = LiveQuizViewModelFactory(
            QuizRepository(ApiClient.getService()),
            LiveQuizManager(
                baseUrl = "https://quiz-app-backend-7m74.onrender.com",
               token = token // <- inject logged-in user id here
            )
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
            var showJoinDialog by remember { mutableStateOf(false) }

            HomeScreen(
                homeViewModel = homeViewModel,
                dataStoreManager = dataStoreManager,
                onJoinLiveQuiz = {navController.navigate(NavRoutes.JOIN_QUIZ)},
                onJoinQuiz = { },
                onFabClick = { navController.navigate(NavRoutes.CREATE_QUIZ_SETUP) },
                onNavHome = {
                    navController.navigate(NavRoutes.HOME) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onNavLibrary = { navController.navigate(NavRoutes.COMING_SOON) },
                onNavLeaderboard = { navController.navigate(NavRoutes.COMING_SOON) },
                onNavMe = { navController.navigate(NavRoutes.COMING_SOON) },
                onGetStartedClick = { navController.navigate(NavRoutes.DAILY_QUIZ) }
            )
        }

        composable(NavRoutes.JOIN_QUIZ) {
            val token = tokenManager.getToken() ?: ""
            val context = LocalContext.current
            val quizManager = remember { LiveQuizManager("https://quiz-app-backend-7m74.onrender.com", token) }

            JoinQuizDialog(
                onDismiss = { navController.popBackStack() },
                onJoinQuiz = { code ->

                    liveQuizViewModel.startQuiz(code)
                    // use userId from datastore/auth
                    Log.d("Token Check", "Token: ${token}")
                    Log.d("func call ckeck", "Token: ${token}")

                    val userId = extractUserIdFromToken(token)// replace with actual logged-in userId
                    Log.d("Token Check", "userID: ${userId}")

                    quizManager.connect(code, userId)

                    // move to Live Quiz screen
                    navController.navigate(NavRoutes.LIVE_QUIZ)
                }
            )
        }

        composable(NavRoutes.LIVE_QUIZ) {
            val token = tokenManager.getToken() ?: ""
            val userId = extractUserIdFromToken(token)

            LiveQuizSimpleScreen(
                viewModel = liveQuizViewModel,
                quizId = "95",   // Or pass dynamically via arguments
                userId = userId
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



        composable(NavRoutes.COMING_SOON) {
            ComingSoonScreen(onBack = { navController.popBackStack() })
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

private fun LiveQuizManager.connect(
    string: String,
    i: Int?
) {
}
