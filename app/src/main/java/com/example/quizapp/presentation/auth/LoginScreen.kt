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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import java.nio.file.Files.size


@Composable
fun LoginScreen(
    onLogin: (scholarId: String, password: String, remember: Boolean) -> Unit = { _, _, _ -> },
    onResetPassword: () -> Unit = {},
    onSignup: () -> Unit = {}
) {
    var scholarId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val loginEnabled = scholarId.isNotBlank() && password.isNotBlank()

    Box(modifier = Modifier.fillMaxSize()) {
        // Top purple header with subtle circles
        TopDecorHeader(height = 220.dp)

        // Card that overlaps the header
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(150.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
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
                        color = Color(0xFF111111),
                        letterSpacing = 0.2.sp
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "EXISTING ACCOUNT",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111111),
                        letterSpacing = 0.2.sp
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
                        value = scholarId,
                        onValueChange = { scholarId = it },
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
                    Text(
                        text = "Password",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF333333)
                    )
                    Spacer(Modifier.height(3.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Enter your password") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val label = if (passwordVisible) "Hide" else "Show"
                            TextButton(onClick = { passwordVisible = !passwordVisible }) {
                                Text(label)
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        )
                    )

                    Spacer(Modifier.height(8.dp))

                    // Remember + Reset row
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

                    Spacer(Modifier.height(90.dp))

                    // Login button
                    Button(
                        onClick = { onLogin(scholarId, password, rememberMe) },
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
                        Text(
                            text = "Login",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold

                        )
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }

            Spacer(Modifier.height(18.dp))

            // Bottom "Signup for free"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Don’t have an account? ")
                TextButton(onClick = onSignup) {
                    Text("Signup for free")
                }
            }
            DecorativeCircles()
        }
    }
}

@Composable
private fun TopDecorHeader(height: Dp) {
    val hPx = with(LocalDensity.current) { height.toPx() }
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

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.03f),
                            Color.Transparent
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(30f, 30f),
                        tileMode = TileMode.Repeated
                    )
                )
        )

        DecorativeCircle(Offset( -120f, hPx * 0.05f), 260f, Color.White.copy(alpha = 0.08f))
        DecorativeCircle(Offset( max(300f, hPx), hPx * 0.35f), 220f, Color.White.copy(alpha = 0.10f))
        DecorativeCircle(Offset( 80f, hPx * 0.75f), 140f, Color.White.copy(alpha = 0.12f))
    }
}

@Composable
private fun DecorativeCircle(center: Offset, radius: Float, color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(color = color, radius = radius, center = center)
    }
}

@Preview(showBackground = true, widthDp = 420)
@Composable
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen()
    }
}

