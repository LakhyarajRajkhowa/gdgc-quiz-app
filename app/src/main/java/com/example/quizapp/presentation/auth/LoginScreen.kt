package com.example.quizapp.presentation.auth

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onResetPassword: () -> Unit = {}
) {
    var scholarID by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    val authState by authViewModel.authState.collectAsState()

    val loginEnabled = scholarID.isNotBlank() && password.isNotBlank()

    // 🔹 React to login success like in your old screen
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onLoginSuccess((authState as AuthState.Success).token)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Decorative purple header
        TopDecorHeader(height = 220.dp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(150.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 40.dp, vertical = 50.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "LOGIN TO YOUR",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111111)
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "EXISTING ACCOUNT",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111111)
                    )

                    Spacer(Modifier.height(24.dp))

                    // Scholar ID
                    Text(
                        text = "Scholar ID",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF333333)
                    )
                    Spacer(Modifier.height(3.dp))
                    OutlinedTextField(
                        value = scholarID,
                        onValueChange = { scholarID = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter your Scholar ID") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(Modifier.height(20.dp))

                    // Password
                    Text("Password", style = MaterialTheme.typography.labelLarge, color = Color(0xFF333333))
                    Spacer(Modifier.height(3.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter your password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    Spacer(Modifier.height(8.dp))

                    // Remember me + Reset password
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                            Text("Remember me")
                        }
                        TextButton(onClick = onResetPassword) {
                            Text("Reset Password")
                        }
                    }

                    Spacer(Modifier.height(30.dp))

                    // 🔹 Auth feedback
                    when (authState) {
                        is AuthState.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                            Spacer(Modifier.height(16.dp))
                        }
                        is AuthState.Error -> {
                            Text(
                                text = (authState as AuthState.Error).error,
                                color = Color.Red,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                        is AuthState.Message -> {
                            Text(
                                text = (authState as AuthState.Message).message,
                                color = Color.Gray,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                        else -> {}
                    }

                    // Login button
                    Button(
                        onClick = { authViewModel.login(scholarID, password) },
                        enabled = loginEnabled,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White.copy(alpha = 0.6f)
                        )
                    ) {
                        Text("Login", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }

            Spacer(Modifier.height(18.dp))

            // Signup link
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Don’t have an account? ")
                TextButton(onClick = onRegisterClick) {
                    Text("Signup for free")
                }
            }
        }
    }
}

@Composable
private fun TopDecorHeader(height: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF7D4CFF), Color(0xFF6A3DF0)),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            )
    ) {
        // Decorative circles
        DecorativeCircle(Offset(-120f, 80f), 260f, Color.White.copy(alpha = 0.08f))
        DecorativeCircle(Offset(300f, 120f), 220f, Color.White.copy(alpha = 0.10f))
        DecorativeCircle(Offset(80f, 180f), 140f, Color.White.copy(alpha = 0.12f))
    }
}

@Composable
private fun DecorativeCircle(center: Offset, radius: Float, color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(color = color, radius = radius, center = center)
    }
}
