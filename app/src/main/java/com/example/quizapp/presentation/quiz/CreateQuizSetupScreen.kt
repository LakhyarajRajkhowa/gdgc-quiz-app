package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.TextFieldValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuizSetupScreen(
    onNext: (String, Int, Int) -> Unit, // updated
    onBack: () -> Unit
)
{
    var quizName by remember { mutableStateOf(TextFieldValue("")) }
    var numQuestions by remember { mutableStateOf(TextFieldValue("")) }
    var timeLimit by remember { mutableStateOf(TextFieldValue("30")) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create a Quiz",
            fontSize = 28.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Quiz Name
        OutlinedTextField(
            value = quizName,
            onValueChange = { quizName = it },
            label = { Text("Quiz Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Number of Questions
        OutlinedTextField(
            value = numQuestions,
            onValueChange = { numQuestions = it },
            label = { Text("Number of Questions") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Time per Question
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = timeLimit,
            onValueChange = { timeLimit = it },
            label = { Text("Time Limit (seconds)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val num = numQuestions.text.toIntOrNull() ?: 0
                val time = timeLimit.text.toIntOrNull() ?: 30 // fallback to 60 if invalid
                if (quizName.text.isNotBlank() && num > 0) {
                    onNext(quizName.text, num, time)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7D4CFF),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Next",
                fontSize = 18.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
        }
    }
}
