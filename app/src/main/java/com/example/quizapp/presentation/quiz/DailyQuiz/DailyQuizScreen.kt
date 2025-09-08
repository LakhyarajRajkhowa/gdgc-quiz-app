package com.example.quizapp.presentation.quiz.DailyQuiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.HtmlCompat
import com.example.quizapp.R
import com.example.quizapp.data.models.Question
import kotlinx.coroutines.delay


@Composable
fun DailyQuizStartScreen(
    totalQuestions: Int = 10,
    secondsPerQuestion: Int = 30,
    onBack: () -> Unit = {},
    onBegin: () -> Unit = {},
) {

    Scaffold(
        topBar = {
            Surface(
                tonalElevation = 0.dp,
                color = Color(0xFF7D4CFF)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(start = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Questions:",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = totalQuestions.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
                )

                Text(
                    text = "Time for each question:",
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = "$secondsPerQuestion seconds",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
                )

                Button(
                    onClick = onBegin,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(48.dp)
                        .width(240.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7D4CFF),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Begin Quiz!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.begin_quiz),
                contentDescription = "Illustration",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 90.dp)
                    .height(360.dp)
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyQuizScreen(
    questions: List<Question>,
    onBack: () -> Unit = {},
    onFinish: (score: Int, total: Int) -> Unit = { _, _ -> }
) {
    var showLeaveDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = true) { showLeaveDialog = true }

    val perQuestionSelected = remember { mutableStateListOf<Int?>().apply { repeat(questions.size) { add(null) } } }
    val perQuestionSubmitted = remember { mutableStateListOf<Boolean>().apply { repeat(questions.size) { add(false) } } }
    val perQuestionTimeUp = remember { mutableStateListOf<Boolean>().apply { repeat(questions.size) { add(false) } } }

    var currentIndex by rememberSaveable { mutableStateOf(0) }
    var timeLeft by rememberSaveable { mutableStateOf(30) }
    var timerActive by rememberSaveable { mutableStateOf(true) }

    val purple = Color(0xFF7D4CFF)
    val correctGreen = Color(0xFF4CAF50)
    val incorrectRed = Color(0xFFF44336)

    val totalQuestions = questions.size
    val progress = (currentIndex + 1) / totalQuestions.toFloat()

    // Timer
    LaunchedEffect(currentIndex, timerActive) {
        timeLeft = 30
        perQuestionTimeUp[currentIndex] = false
        timerActive = true
        while (timeLeft > 0 && timerActive) {
            delay(1000L)
            timeLeft--
        }
        if (timeLeft == 0 && timerActive) {
            perQuestionTimeUp[currentIndex] = true
            perQuestionSubmitted[currentIndex] = true
            timerActive = false
        }
    }

    val currentQuestion = questions[currentIndex]
    val selectedIndex = perQuestionSelected[currentIndex]
    val submitted = perQuestionSubmitted[currentIndex]
    val timeUp = perQuestionTimeUp[currentIndex]

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
                            text = "Question ${currentIndex + 1} of $totalQuestions",
                            color = Color.White,
                            fontSize = 14.sp
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
                        if (!submitted && !timeUp) {
                            perQuestionSubmitted[currentIndex] = true
                            timerActive = false
                        }
                    },
                    enabled = (selectedIndex != null) && !submitted && !timeUp,
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(0.6f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (submitted || timeUp) purple.copy(alpha = 0.8f) else purple,
                        disabledContainerColor = Color(0xFFE8D8FF)
                    )
                ) {
                    Text(
                        text = when {
                            timeUp -> "Time's Up!"
                            submitted -> "Submitted ✓"
                            else -> "Submit"
                        },
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Navigation buttons
                Row(
                    modifier = Modifier.fillMaxWidth().navigationBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            if (currentIndex > 0) {
                                currentIndex--
                                timerActive = true
                            }
                        },
                        enabled = currentIndex > 0 && (timeUp || submitted)
                    ) { Text("Previous") }

                    TextButton(
                        onClick = {
                            if (currentIndex < questions.lastIndex) {
                                currentIndex++
                                timerActive = true
                            } else {
                                val score = questions.indices.count { idx ->
                                    perQuestionSubmitted[idx] &&
                                            perQuestionSelected[idx] == questions[idx].correctIndex
                                }
                                onFinish(score, questions.size)
                            }
                        },
                        enabled = timeUp || submitted
                    ) { Text(if (currentIndex < questions.lastIndex) "Next" else "Finish") }
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        // Scrollable question area
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

            // Question text
            Text(
                text = HtmlCompat.fromHtml(currentQuestion.question, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(Modifier.height(20.dp))

            // Options
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                currentQuestion.options.forEachIndexed { idx, option ->
                    val correctAnswerIndex = currentQuestion.correctIndex
                    val selected = selectedIndex == idx

                    val borderColor = when {
                        timeUp || submitted -> when {
                            idx == correctAnswerIndex -> correctGreen
                            selected && idx != correctAnswerIndex -> incorrectRed
                            else -> Color(0xFFBDBDBD)
                        }
                        selected -> Color(0xFF3A86FF)
                        else -> Color(0xFFBDBDBD)
                    }

                    OutlinedOption(
                        letter = ('A' + idx).toString() + ".",
                        text = HtmlCompat.fromHtml(option, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                        onClick = { if (!submitted && !timeUp) perQuestionSelected[currentIndex] = idx },
                        selected = selected,
                        borderColor = borderColor,
                        enabled = !submitted && !timeUp,
                        correctGreen = correctGreen,
                        incorrectRed = incorrectRed,
                        showIndicator = timeUp || submitted,
                        isCorrectAnswer = idx == correctAnswerIndex,
                        isIncorrectSelected = selected && idx != correctAnswerIndex,
                        timeUp = timeUp
                    )
                }
            }

            Spacer(Modifier.height(80.dp)) // keep last option visible above bottomBar
        }
    }

    if (showLeaveDialog) {
        LeaveQuizDialog(
            onContinue = { showLeaveDialog = false },
            onLeave = { showLeaveDialog = false; onBack() },
            onDismiss = { showLeaveDialog = false }
        )
    }
}


@Composable
private fun OutlinedOption(
    letter: String,
    text: String,
    onClick: () -> Unit,
    selected: Boolean,
    borderColor: Color,
    enabled: Boolean,
    correctGreen: Color,
    incorrectRed: Color,
    showIndicator: Boolean,
    isCorrectAnswer: Boolean,
    isIncorrectSelected: Boolean,
    timeUp: Boolean
) {
    val background = if (selected) Color(0xFFF6F6FF) else Color.White
    Surface(
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp)
            .border(BorderStroke(2.dp, borderColor), shape = RoundedCornerShape(8.dp))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        color = background
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = letter, fontWeight = FontWeight.Bold, modifier = Modifier.width(36.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, fontSize = 17.sp)

            if (timeUp && showIndicator) {
                Spacer(modifier = Modifier.weight(1f))
                when {
                    isCorrectAnswer -> Text(
                        text = "✓",
                        color = correctGreen,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    isIncorrectSelected -> Text(
                        text = "✗",
                        color = incorrectRed,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    }
}




@Composable
fun LeaveQuizDialog(
    onContinue: () -> Unit,
    onLeave: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Color(0xFFFFA000),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        "Wait, You are almost there! Don’t give up",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Divider(color = Color.Gray, thickness = 1.dp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onContinue,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Continue", color = Color.White, fontSize = 16.sp)
                    }

                    Button(
                        onClick = onLeave,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
                    ) {
                        Text("Leave Quiz", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
