package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizapp.presentation.quiz.QuizViewModel
import kotlinx.coroutines.delay

@Composable
fun QuizScreen(viewModel: QuizViewModel = viewModel()) {
    val question = viewModel.currentQuestion.collectAsState().value
    val timeLeft = viewModel.timeLeft.collectAsState().value

    // Countdown effect
    LaunchedEffect(question) {
        viewModel.resetTimer()
        while (viewModel.timeLeft.value > 0) {
            delay(1000)
            viewModel.tick()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (question != null) {
            Text("Time left: $timeLeft s", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(20.dp))

            Text(question.question, style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(20.dp))

            question.options.forEach { option ->
                Button(
                    onClick = { viewModel.nextQuestion() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Text(option)
                }
            }
        } else {
            Text("Loading questions...")
        }
    }
}
