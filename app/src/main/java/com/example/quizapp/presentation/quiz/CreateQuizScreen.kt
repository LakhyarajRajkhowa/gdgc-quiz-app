package com.example.quizapp.presentation.quiz

import android.R.bool
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuestionScreen(
    quizTitle: String,
    totalQuestions: Int,
    onBack: () -> Unit,
    onFinishQuiz: () -> Unit
) {
    var questionText by remember { mutableStateOf("") }
    var optionA by remember { mutableStateOf("") }
    var optionB by remember { mutableStateOf("") }
    var optionC by remember { mutableStateOf("") }
    var optionD by remember { mutableStateOf("") }
    var selectedAnswer by remember { mutableStateOf("A") }

    val questions = remember { mutableStateListOf<QuestionRequest>() }
    var currentIndex by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }

    var quizCode by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val progress = (currentIndex + 1) / totalQuestions.toFloat()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Questions", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF7D4CFF))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Progress bar + question counter
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = Color(0xFF7D4CFF),
                trackColor = Color.LightGray.copy(alpha = 0.3f)
            )
            Text("Question ${currentIndex + 1} of $totalQuestions")

            // Question input
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

            Text("Choose the correct option")
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

            Spacer(modifier = Modifier.weight(1f))

            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(16.dp))
                }
            }

            Button(
                onClick = {
                    if (questionText.isNotBlank() && optionA.isNotBlank() &&
                        optionB.isNotBlank() && optionC.isNotBlank() && optionD.isNotBlank()
                    ) {
                        val correctAnswer = when (selectedAnswer) {
                            "A" -> optionA
                            "B" -> optionB
                            "C" -> optionC
                            "D" -> optionD
                            else -> optionA
                        }
                        questions.add(
                            QuestionRequest(questionText, listOf(optionA, optionB, optionC, optionD), correctAnswer, 60)
                        )

                        if (currentIndex + 1 == totalQuestions) {
                            // send quiz to backend
                            isLoading = true
                            sendQuizToBackend(quizTitle, questions) { success, error ->
                                isLoading = false
                                if (success != null) {
                                    quizCode = success  // ✅ store code in flag
                                } else {
                                    errorMessage = error // ✅ store error in flag
                                }
                            }

                        } else {
                            // go to o question
                            currentIndex++
                            questionText = ""; optionA = ""; optionB = ""; optionC = ""; optionD = ""
                            selectedAnswer = "A"
                        }
                    } else {
                        message = "❌ Fill all fields first"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7D4CFF))
            ) {
                Text(if (currentIndex + 1 == totalQuestions) "Finish Quiz" else "Next", color = Color.White)
            }



            val context = LocalContext.current

            if (quizCode != null) {
                AlertDialog(
                    onDismissRequest = { /* don’t dismiss outside */ },
                    confirmButton = {
                        Row {
                            Button(onClick = {
                                // Copy code to clipboard
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Quiz Code", quizCode)
                                clipboard.setPrimaryClip(clip)
                            }) {
                                Text("Copy")
                            }
                            Spacer(Modifier.width(8.dp))
                            Button(onClick = {
                                quizCode = null
                                onFinishQuiz() // ✅ navigate only when OK is pressed
                            }) {
                                Text("OK")
                            }
                        }
                    },
                    title = { Text("Quiz Created!") },
                    text = { Text("Joining Code: $quizCode") }
                )
            }

            if (errorMessage != null) {
                AlertDialog(
                    onDismissRequest = { errorMessage = null },
                    confirmButton = {
                        Button(onClick = { errorMessage = null }) {
                            Text("OK")
                        }
                    },
                    title = { Text("Error") },
                    text = { Text(errorMessage ?: "") }
                )
            }



        }
    }
}


@Composable
fun OptionTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Text(label, fontWeight = FontWeight.Bold) },
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
                    val body = response.body()
                    val codeMessage = if (body?.code != null) {
                        "${body.code}"
                    } else {
                        "${body?.quizId}"
                    }
                    // ❌ don’t call onFinishQuiz() here
                    onResult(codeMessage, null)
                } else {
                    onResult(null, "Failed: ${response.code()} ${response.message()}")
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                onResult(null, "Error: ${e.message}")
            }
        }
    }
}
