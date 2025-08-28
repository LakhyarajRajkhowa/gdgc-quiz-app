package com.example.quizapp.presentation.home

import HomeUiState
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizapp.R
import com.example.quizapp.data.DataStoreManager
import com.example.quizapp.presentation.auth.AuthState
import com.example.quizapp.presentation.home.components.*
import com.example.quizapp.presentation.navigation.BottomNavItem

private val Purple1 = Color(0xFF7D4CFF)
private val Purple2 = Color(0xFF6A3DF0)
private val SoftYellow = Color(0xFFF6C66B)

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    dataStoreManager: DataStoreManager,
    onJoinQuiz: () -> Unit = {},
    onFabClick: () -> Unit = {},
    onNavHome: () -> Unit = {},
    onNavLibrary: () -> Unit = {},
    onNavLeaderboard: () -> Unit = {},
    onNavMe: () -> Unit = {},
    onGetStartedClick: () -> Unit = {}
) {
    val state by homeViewModel.uiState.collectAsState()
    var selectedItem by remember { mutableStateOf(BottomNavItem.HOME) } // <-- FIXED

    LaunchedEffect(Unit) {
        val token = dataStoreManager.getToken()
        if (token != null) {
            homeViewModel.loadHomeData(token)
        } else {
            // fallback: maybe navigate to Login
            Log.d("HomeScreenTest", "token invalid")
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick,
                containerColor = Purple1,
                contentColor = Color.White,
                modifier = Modifier
                    .size(64.dp)
                    .offset(y = (74).dp),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomNavigationBar(selectedItem = selectedItem,
                onItemSelected = { item ->
                    selectedItem = item
                    when (item) {
                        BottomNavItem.HOME -> onNavHome()
                        BottomNavItem.LIBRARY -> onNavLibrary()
                        BottomNavItem.LEADERBOARD -> onNavLeaderboard()
                        BottomNavItem.ME -> onNavMe()
                    }
                })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            UserHeader(username = state.username)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-28).dp) // lift up to overlap header
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(top = 18.dp, bottom = 18.dp, start = 14.dp, end = 14.dp)
            ){
                LiveQuizCard(
                title = state.liveQuizTitle,
                onJoinQuiz = onJoinQuiz
            )

                ProgressSection(
                    ranking = state.ranking,
                    totalScore = state.totalScore,
                    quizzesAttempted = state.quizzesAttempted,
                    coins = state.coins,
                    progressPercent = state.progressPercent
                )

                DailyChallengeCard(
                    challengeText = state.dailyChallenge ?: "No challenge today",
                    onGetStartedClick = onGetStartedClick
                )
            }

        }
    }
}

@Preview
@Composable
fun previewHomescreen(){
    MaterialTheme {
        HomeScreenUi(onJoinQuiz = {}, onFabClick = {}, performance = "65%")
    }
}