package com.example.quizapp.presentation.quiz.LiveQuiz

import android.text.Html
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.example.quizapp.R
import com.example.quizapp.presentation.components.LeaveQuizDialog
import com.example.quizapp.presentation.components.OutlinedOption
import kotlinx.coroutines.delay
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveQuizScreen(
    viewModel: LiveQuizViewModel,
    quizId: String,
    userId: Int?,
    onNavigateToLeaderboard: (quizId: String) -> Unit
) {
    val quizStarted by viewModel.quizStarted.collectAsState()
    val newQuestion by viewModel.newQuestion.collectAsState()
    val questionEnded by viewModel.questionEnded.collectAsState()
    val quizEnded by viewModel.quizEnded.collectAsState()
    val error by viewModel.error.collectAsState()

    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var timeUp by remember { mutableStateOf(false) }
    var showLeaveDialog by remember { mutableStateOf(false) }
    var currentQuestionId by remember { mutableStateOf<String?>(null) }
    var timeLeft by remember { mutableStateOf(0) } // seconds for countdown

    // Reset state for each new question
    LaunchedEffect(newQuestion) {
        newQuestion?.let {
            currentQuestionId = it.optString("id")
            selectedIndex = null
            timeUp = false
            timeLeft = it.optInt("time", 10) // get time from server
        }
    }

    // Countdown timer
    LaunchedEffect(timeLeft, newQuestion) {
        while (timeLeft > 0 && !timeUp) {
            kotlinx.coroutines.delay(1000L)
            timeLeft -= 1
        }
        if (timeLeft <= 0) {
            timeUp = true
        }
    }

    // Mark question as ended
    LaunchedEffect(questionEnded) {
        questionEnded?.let {
            if (it.optString("questionId") == currentQuestionId) {
                timeUp = true
            }
        }
    }

    BackHandler { showLeaveDialog = true }

    if (showLeaveDialog) {
        LeaveQuizDialog(
            onContinue = { showLeaveDialog = false },
            onLeave = {
                viewModel.disconnect()
                onNavigateToLeaderboard(quizId)
            },
            onDismiss = { showLeaveDialog = false }
        )
    }

    val purple = Color(0xFF7D4CFF)
    val correctGreen = Color(0xFF4CAF50)
    val incorrectRed = Color(0xFFF44336)

    val totalQuestions = 1 // Placeholder for live quiz
    val progress = 1f      // Single question progress for UI

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LinearProgressIndicator(
                            progress = progress,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp),
                            color = Color.White,
                            trackColor = purple.copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Live Quiz",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { showLeaveDialog = true }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = purple)
            )
        },
        containerColor = Color.White,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Submit button
                Button(
                    onClick = {
                        selectedIndex?.let { idx ->
                            viewModel.submitAnswer(
                                quizId,
                                newQuestion?.optString("id") ?: "",
                                userId,
                                newQuestion?.optJSONArray("options")?.optString(idx) ?: ""
                            )
                        }
                    },
                    enabled = selectedIndex != null && !timeUp,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(0.6f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (timeUp) purple.copy(alpha = 0.8f) else purple,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = if (timeUp) "Time's Up!" else "Submit Answer",
                        fontSize = 18.sp
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))

            // Timer
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = String.format("%02d:%02d", timeLeft / 60, timeLeft % 60),
                    fontSize = 18.sp,
                    color = if (timeLeft <= 5) Color.Red else Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "timer",
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            // Question
            newQuestion?.let { question ->
                Text(
                    text = HtmlCompat.fromHtml(question.optString("text"), HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )

                Spacer(Modifier.height(20.dp))
                val options = question.optJSONArray("options") ?: JSONArray()
                val letters = listOf("A", "B", "C", "D")
                val correctAnswer = question.optString("correct") // available only after timeUp

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    for (i in 0 until options.length()) {
                        val optionText = options.optString(i)
                        val selected = selectedIndex == i

                        val borderColor = when {
                            timeUp -> when {
                                optionText == correctAnswer -> Color(0xFF4CAF50) // green correct
                                selected && optionText != correctAnswer -> Color(0xFFF44336) // red wrong
                                else -> Color(0xFFBDBDBD) // gray
                            }
                            selected -> Color(0xFF3A86FF) // blue selection
                            else -> Color(0xFFBDBDBD) // gray default
                        }

                        OutlinedOption(
                            letter = if (i < letters.size) letters[i] + "." else "${i + 1}.",
                            text = optionText,
                            onClick = { if (!timeUp) selectedIndex = i },
                            selected = selected,
                            borderColor = borderColor,
                            enabled = !timeUp,
                            correctGreen = Color(0xFF4CAF50),
                            incorrectRed = Color(0xFFF44336),
                            showIndicator = timeUp,
                            isCorrectAnswer = timeUp && optionText == correctAnswer,
                            isIncorrectSelected = timeUp && selected && optionText != correctAnswer,
                            timeUp = timeUp
                        )
                    }
                }
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}
