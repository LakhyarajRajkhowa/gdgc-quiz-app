package com.example.quizapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizapp.presentation.home.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    onEnterQuiz: () -> Unit
) {
    val username by viewModel.username.collectAsState()
    val scholarId by viewModel.scholarId.collectAsState()
    val quizStarted by viewModel.quizStarted.collectAsState()

    // Random icon for profile
    val profileIcons = listOf("😎", "🤓", "🚀", "🐱", "🐧", "🦉")
    val profileIcon = remember { profileIcons.random() }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile section
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(profileIcon, style = MaterialTheme.typography.headlineLarge)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(username, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("Scholar ID: $scholarId", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(32.dp))

        // Announcement dashboard
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Announcements", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    if (quizStarted) "The quiz has started! 🎉" else "No quiz available right now.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (quizStarted) {
            Button(onClick = onEnterQuiz) {
                Text("Enter Quiz")
            }
        } else {
            Button(
                onClick = { viewModel.startQuiz() } // controlled by ViewModel
            ) {
                Text("Simulate Quiz Start (for testing purpose only)")
            }
        }
    }
}
