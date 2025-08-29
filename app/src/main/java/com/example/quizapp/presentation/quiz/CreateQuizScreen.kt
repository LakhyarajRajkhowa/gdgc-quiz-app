package com.example.quizapp.presentation.quiz

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.quizapp.data.models.Question
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuestionScreen(
    onBack: () -> Unit,
    onFinishQuiz: (Question) -> Unit,
    onNext: (Question) -> Unit
) {
    var questionText by remember { mutableStateOf("") }
    var optionA by remember { mutableStateOf("") }
    var optionB by remember { mutableStateOf("") }
    var optionC by remember { mutableStateOf("") }
    var optionD by remember { mutableStateOf("") }
    var selectedAnswer by remember { mutableStateOf("A") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF7D4CFF),
                    navigationIconContentColor = Color.White
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
            // Background circles
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                drawCircle(
                    color = Color(0xFF7D4CFF).copy(alpha = 0.07f),
                    radius = w * 0.5f,
                    center = Offset(x = w * 0.28f, y = h * 0.99f)
                )
                drawCircle(
                    color = Color(0xFF7D4CFF).copy(alpha = 0.07f),
                    radius = w * 0.3f,
                    center = Offset(x = w * 0.86f, y = h * 0.97f)
                )
                drawCircle(
                    color = Color(0xFF7D4CFF).copy(alpha = 0.07f),
                    radius = w * 0.3f,
                    center = Offset(x = w * 0.86f, y = h * 0.001f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 45.dp, bottom = 65.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Enter your question", fontWeight = FontWeight.Bold, fontSize = 26.sp)

                Text("Q.1", fontSize = 20.sp)
                OutlinedTextField(
                    value = questionText,
                    onValueChange = { questionText = it },
                    placeholder = { Text("Enter your question", fontSize = 18.sp) },
                    modifier = Modifier.fillMaxWidth()
                )

                OptionTextField("A.", optionA) { optionA = it }
                OptionTextField("B.", optionB) { optionB = it }
                OptionTextField("C.", optionC) { optionC = it }
                OptionTextField("D.", optionD) { optionD = it }

                Spacer(Modifier.height(8.dp))

                Text("Choose the correct option", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("A", "B", "C", "D").forEachIndexed { index, option ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedAnswer == option,
                                onClick = { selectedAnswer = option }
                            )
                            Text(option, fontSize = 18.sp)
                        }
                    }
                }

                Spacer(Modifier.weight(1f))

                val correctIndex = when (selectedAnswer) {
                    "A" -> 0
                    "B" -> 1
                    "C" -> 2
                    "D" -> 3
                    else -> 0
                }

                val currentQuestion = Question(
                    question = questionText,
                    options = listOf(optionA, optionB, optionC, optionD),
                    correctIndex = correctIndex
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    Button(
                        onClick = { onFinishQuiz(currentQuestion) },
                        shape = RoundedCornerShape(10),
                        modifier = Modifier
                            .height(48.dp)
                            .width(150.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7D4CFF))
                    ) {
                        Text("Finish Quiz", color = Color.White, fontSize = 18.sp)
                    }

                    Button(
                        onClick = { onNext(currentQuestion) },
                        shape = RoundedCornerShape(10),
                        modifier = Modifier
                            .height(48.dp)
                            .width(150.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7D4CFF))
                    ) {
                        Text("Next", color = Color.White, fontSize = 18.sp)
                    }
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
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        placeholder = { Text("Enter your option", fontSize = 18.sp) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun CreateQuestionScreenPreview() {
    MaterialTheme {
        CreateQuestionScreen(
            onBack = {},
            onFinishQuiz = {},
            onNext = {}
        )
    }
}

@Composable
fun CreateQuizCodeDialogContent(
    code: String,
    onCopy: (String) -> Unit,
    onShare: (String) -> Unit
) {

    var code by remember { mutableStateOf(code) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 4.dp,
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(20.dp, end = 20.dp, top = 35.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Joining code",
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(Modifier.height(12.dp))

            // Text field with copy button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = { onCopy(code) },
                    shape = RoundedCornerShape(10),
                    modifier = Modifier
                        .height(48.dp)
                        .width(130.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7D4CFF))
                ) {
                    Text("Copy link", fontSize = 18.sp, color = Color.White)
                }
            }

            Spacer(Modifier.height(20.dp))

            // Share button
            Button(
                onClick = { onShare(code) },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(48.dp),
                shape = RoundedCornerShape(10),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Share link", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuizCodeDialog(
    code: String,
    onDismiss: () -> Unit,
    onCopy: (String) -> Unit,
    onShare: (String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true, dismissOnBackPress = true)
    ) {
        CreateQuizCodeDialogContent(code, onCopy, onShare)
    }
}


@Preview()
@Composable
fun JoiningCodeDialogPreview() {
    MaterialTheme {
        CreateQuizCodeDialogContent(
            code = "8YLCZP",
            onCopy = {},
            onShare = {}
        )
    }
}