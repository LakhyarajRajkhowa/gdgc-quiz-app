package com.example.quizapp.presentation.quiz.LiveQuiz


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LiveQuizSimpleScreen(
    viewModel: LiveQuizViewModel,
    quizId: String,
    userId: Int?
) {
    val quizStarted by viewModel.quizStarted.collectAsState()
    val newQuestion by viewModel.newQuestion.collectAsState()
    val questionEnded by viewModel.questionEnded.collectAsState()
    val quizEnded by viewModel.quizEnded.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.connectToServer(quizId, userId)
        viewModel.joinQuiz(quizId, userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Live Quiz Screen", style = MaterialTheme.typography.titleLarge)

        quizStarted?.let {
            Text("✅ Quiz Started: $it", color = Color.Green)
        }

        newQuestion?.let {
            Text("📌 New Question: ${it.optString("text")}", color = Color.Blue)
        }

        questionEnded?.let {
            Text("⏱ Question Ended: ${it.optString("questionId")}", color = Color.Gray)
        }

        quizEnded?.let {
            Text("🏆 Quiz Ended: ${it.optString("someField")}", color = Color.Magenta)
        }

        error?.let {
            Text("⚠ Error: $it", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.submitAnswer(quizId, newQuestion?.optString("id") ?: "", userId, "OptionA")
        }, enabled = newQuestion != null) {
            Text("Submit Answer")
        }
    }
}
