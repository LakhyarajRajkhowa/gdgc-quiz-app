package com.example.quizapp.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quizapp.data.DailyQuizApi.QuizItem
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.sp



@Composable
    fun QuizQuestionCard(quiz: QuizItem, timer: Int) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Time left: ${timer}s", fontWeight = FontWeight.Bold, color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))

            Text(quiz.question, fontSize = 18.sp, fontWeight = FontWeight.Medium)

            Spacer(modifier = Modifier.height(16.dp))

            val options = remember {
                (quiz.incorrect_answers + quiz.correct_answer).shuffled()
            }

            options.forEach { option ->
                Button(
                    onClick = { /* check answer */ },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text(option)
                }
            }
        }
    }

