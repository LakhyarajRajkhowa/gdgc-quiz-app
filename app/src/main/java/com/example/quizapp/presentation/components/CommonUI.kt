package com.example.quizapp.presentation.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text)
    }
}
