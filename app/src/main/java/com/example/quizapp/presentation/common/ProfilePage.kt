package com.example.quizapp.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.presentation.auth.DecorativeCircle
import com.example.quizapp.presentation.home.Purple1
import com.example.quizapp.presentation.home.components.BottomNavigationBar
import com.example.quizapp.presentation.navigation.BottomNavItem
import com.example.quizapp.presentation.home.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavHome: () -> Unit = {},
    onNavLibrary: () -> Unit = {},
    onNavLeaderboard: () -> Unit = {},
    onNavMe: () -> Unit = {}
) {

    var selectedItem by remember { mutableStateOf(BottomNavItem.ME) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            Box(Modifier.navigationBarsPadding()) {
                FloatingActionButton(
                    onClick = { showBottomSheet = true },
                    containerColor = Purple1,
                    contentColor = Color.White,
                    modifier = Modifier
                        .size(54.dp)
                        .offset(y = (108).dp),
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }

            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            Box(Modifier.navigationBarsPadding()) {
                BottomNavigationBar(
                    selectedItem = BottomNavItem.ME,
                    onItemSelected = { item ->
                        selectedItem = item
                        when (item) {
                            BottomNavItem.HOME -> onNavHome()
                            BottomNavItem.LIBRARY -> onNavLibrary()
                            BottomNavItem.LEADERBOARD -> onNavLeaderboard()
                            BottomNavItem.ME -> onNavMe()
                        }
                    })
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Background circles
            DecorativeCircle(Offset(400f, 0.001f), 320f, Color(0xFF7D4CFF).copy(alpha = 0.09f))
            DecorativeCircle(Offset(80f, 180f), 240f, Color(0xFF7D4CFF).copy(alpha = 0.08f))

            // Foreground content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Picture with status indicator
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF7D4CFF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "YN",
                            fontSize = 30.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Buttons
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedButton(
                        onClick = { /* Edit Profile */ },
                        modifier = Modifier
                            .weight(0.5f)
                            .height(35.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.5.dp, Color.Black),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            "Edit Profile",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }


                    OutlinedButton(
                        onClick = { /* Logout */ },
                        modifier = Modifier
                            .weight(0.5f)
                            .height(35.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.5.dp, Color.Black),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            "Logout",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(92.dp))

                // Name and Scholar ID
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Name: ")
                            }
                            append("Your Name ")
                        },
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Scholar ID: ")
                            }
                            append("2412111 ")
                        },
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}

