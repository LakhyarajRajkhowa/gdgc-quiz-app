package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.quizapp.data.api.ApiClient
import com.example.quizapp.data.models.CreateQuizRequest
import com.example.quizapp.data.models.QuestionRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuestionScreen(
    onBack: () -> Unit,
    onFinishQuiz: () -> Unit,
    onNext: () -> Unit
) {
    var questionText by remember { mutableStateOf("") }
    var optionA by remember { mutableStateOf("") }
    var optionB by remember { mutableStateOf("") }
    var optionC by remember { mutableStateOf("") }
    var optionD by remember { mutableStateOf("") }
    var selectedAnswer by remember { mutableStateOf("A") }

    // 🔹 UI States
    var isLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }

    // 🔹 Hold quiz title + all questions
    var quizTitle by remember { mutableStateOf("General Knowledge Big Quiz") }
    val questions = remember { mutableStateListOf<QuestionRequest>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Quiz", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF7D4CFF)
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 45.dp, bottom = 65.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 🔹 Quiz title
                Text("Quiz Title", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                OutlinedTextField(
                    value = quizTitle,
                    onValueChange = { quizTitle = it },
                    modifier = Modifier.fillMaxWidth()
                )

                // 🔹 Question input
                Text("Enter Question", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                OutlinedTextField(
                    value = questionText,
                    onValueChange = { questionText = it },
                    placeholder = { Text("Enter your question") },
                    modifier = Modifier.fillMaxWidth()
                )

                OptionTextField("A.", optionA) { optionA = it }
                OptionTextField("B.", optionB) { optionB = it }
                OptionTextField("C.", optionC) { optionC = it }
                OptionTextField("D.", optionD) { optionD = it }

                // 🔹 Correct answer selection
                Text("Choose the correct option", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    listOf("A","B","C","D").forEach { opt ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedAnswer == opt,
                                onClick = { selectedAnswer = opt }
                            )
                            Text(opt)
                        }
                    }
                }

                Spacer(Modifier.weight(1f))

                val correctAnswer = when (selectedAnswer) {
                    "A" -> optionA
                    "B" -> optionB
                    "C" -> optionC
                    "D" -> optionD
                    else -> optionA
                }

                val currentQuestion = QuestionRequest(
                    text = questionText,
                    options = listOf(optionA, optionB, optionC, optionD),
                    correct = correctAnswer,
                    time = 60
                )

                // 🔹 Action buttons
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            if (questionText.isNotBlank() && optionA.isNotBlank() &&
                                optionB.isNotBlank() && optionC.isNotBlank() && optionD.isNotBlank()
                            ) {
                                questions.add(currentQuestion)
                            }
                            isLoading = true
                            sendQuizToBackend(quizTitle, questions,
                                onResult = { success, error ->
                                    isLoading = false
                                    message = success ?: error
                                    if (success != null) {
                                        // clear after finishing
                                        questions.clear()
                                        questionText = ""
                                        optionA = ""; optionB = ""; optionC = ""; optionD = ""
                                        selectedAnswer = "A"
                                        onFinishQuiz()
                                    }
                                }
                            )
                        },
                        shape = RoundedCornerShape(10),
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7D4CFF))
                    ) { Text("Finish Quiz", color = Color.White) }

                    Button(
                        onClick = {
                            if (questionText.isNotBlank() && optionA.isNotBlank() &&
                                optionB.isNotBlank() && optionC.isNotBlank() && optionD.isNotBlank()
                            ) {
                                questions.add(currentQuestion)
                                // reset fields for next question
                                questionText = ""
                                optionA = ""; optionB = ""; optionC = ""; optionD = ""
                                selectedAnswer = "A"
                                message = "✅ Question added, enter next!"
                            } else {
                                message = "❌ Please fill all fields before adding."
                            }
                        },
                        shape = RoundedCornerShape(10),
                        modifier = Modifier.width(150.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7D4CFF))
                    ) { Text("Next", color = Color.White) }
                }

                // 🔹 Status message
                message?.let {
                    Text(it, color = if (it.startsWith("✅")) Color.Green else Color.Red)
                }
            }

            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF7D4CFF))
                }
            }
        }
    }
}


@Composable
fun OptionTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Text(label, fontWeight = FontWeight.Bold)
        },
        placeholder = { Text("Enter your option", fontSize = 16.sp) },
        modifier = Modifier.fillMaxWidth()
    )
}

private fun sendQuizToBackend(
    title: String,
    questions: List<QuestionRequest>,
    onResult: (success: String?, error: String?) -> Unit
) {
    val quiz = CreateQuizRequest(title = title, questions = questions)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = ApiClient.getService().createQuiz(quiz)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    onResult("✅ Quiz created successfully!", null)
                } else {
                    onResult(null, "❌ Failed: ${response} ${response}")
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onResult(null, "❌ Error: ${e.message}")
            }
        }
    }
}
