package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.presentation.HomeScreen
import com.example.quizapp.presentation.home.HomeViewModel
import com.example.quizapp.presentation.login.LoginScreen
import com.example.quizapp.presentation.quiz.QuizScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                QuizApp()
            }
        }
    }
}

@Composable
fun QuizApp() {
    val navController = rememberNavController()
    val mainViewModel: HomeViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen { username, scholarId ->
                mainViewModel.setUserInfo(username, scholarId)
                navController.navigate("main")
            }
        }
        composable("main") {
            HomeScreen(
                viewModel = mainViewModel,
                onEnterQuiz = { navController.navigate("quiz") }
            )
        }
        composable("quiz") {
            QuizScreen()
        }
    }
}
