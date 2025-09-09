package com.example.quizapp.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.quizapp.data.DataStoreManager
import com.example.quizapp.data.storage.TokenManager
import com.example.quizapp.data.storage.extractIsAdminFromToken
import com.example.quizapp.data.storage.extractUsernameFromToken
import com.example.quizapp.presentation.home.components.BottomNavigationBar
import com.example.quizapp.presentation.home.components.DailyChallengeCard
import com.example.quizapp.presentation.home.components.LiveQuizCard
import com.example.quizapp.presentation.home.components.ProgressSection
import com.example.quizapp.presentation.home.components.QuizOptionsBottomSheet
import com.example.quizapp.presentation.home.components.UserHeader
import com.example.quizapp.presentation.navigation.BottomNavItem
import kotlinx.coroutines.launch


val Purple1 = Color(0xFF7D4CFF)
private val Purple2 = Color(0xFF6A3DF0)
private val SoftYellow = Color(0xFFF6C66B)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    dataStoreManager: DataStoreManager,
    onJoinLiveQuiz: () -> Unit = {},
    onJoinQuiz: (String) -> Unit = {},
    onFabClick: () -> Unit = {},
    onNavHome: () -> Unit = {},
    onNavLibrary: () -> Unit = {},
    onNavLeaderboard: () -> Unit = {},
    onNavMe: () -> Unit = {},
    onGetStartedClick: () -> Unit = {}
) {
    val state by homeViewModel.uiState.collectAsState()
    var selectedItem by remember { mutableStateOf(BottomNavItem.HOME) } // <-- FIXED
    var showBottomSheet by remember { mutableStateOf(false) }
    var showJoinDialog by remember { mutableStateOf(false) }
    var showCreateQuizCodeDialog by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val tokenManager = remember { TokenManager(context) }
    val token = tokenManager.getToken() ?: ""

    val isAdmin = tokenManager?.let { extractIsAdminFromToken(token) } ?: false

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            QuizOptionsBottomSheet(
                isAdmin = true,
                onCreateQuiz = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        showBottomSheet = false
                        // Navigate to CreateQuestionScreen via the lambda from AppNavHost
                        onFabClick() // This will trigger navController.navigate("createQuestion")
                    }
                },
                onJoinQuiz = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        showBottomSheet = false
                        onJoinQuiz("")
                    }
                }
            )
        }
    }






    Scaffold(
        floatingActionButton = {
            Box(Modifier.navigationBarsPadding()) {
                FloatingActionButton(
                    onClick = { showBottomSheet = true },
                    containerColor = Purple1,
                    contentColor = Color.White,
                    modifier = Modifier
                        .size(54.dp)
                        .offset(y = (108).dp),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }

            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            Box(Modifier.navigationBarsPadding()) {
                BottomNavigationBar(
                    selectedItem = selectedItem,
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
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            UserHeader(username = extractUsernameFromToken(token).toString())

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
                onJoinLiveQuiz = onJoinLiveQuiz
            )

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = Color.LightGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))


                ProgressSection(
                    ranking = state.ranking,
                    totalScore = state.totalScore,
                    quizzesAttempted = state.quizzesAttempted,
                    coins = state.coins,
                    progressPercent = state.progressPercent
                )

                Spacer(modifier = Modifier.height(16.dp))

                DailyChallengeCard(
                    challengeText = state.dailyChallenge ?: "No challenge today",
                    onGetStartedClick = onGetStartedClick
                )
            }

        }
    }
}

//@Preview
//@Composable
//fun previewHomescreen(){
//    MaterialTheme {
//        HomeScreenUi(onJoinQuiz = {}, onFabClick = {}, performance = "65%")
//    }
//}