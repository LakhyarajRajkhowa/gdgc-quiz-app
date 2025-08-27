package com.example.quizapp.presentation.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R

private val Purple1 = Color(0xFF7D4CFF)
private val Purple2 = Color(0xFF6A3DF0)
private val SoftYellow = Color(0xFFF6C66B)

enum class BottomNavItem {
    HOME, LIBRARY, LEADERBOARD, ME
}

@Composable
fun HomeScreenUi(
    onJoinQuiz: () -> Unit = {},
    onFabClick: () -> Unit = {},
    onNavHome: () -> Unit = {},
    onNavLibrary: () -> Unit = {},
    onNavLeaderboard: () -> Unit = {},
    onNavMe: () -> Unit = {},
    performance: String = "65%"
) {
    var selectedItem by remember { mutableStateOf(BottomNavItem.HOME) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick,
                containerColor = Purple1,
                contentColor = Color.White,
                modifier = Modifier
                    .size(64.dp)
                    .offset(y = (74).dp),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                color = Purple2,
                modifier = Modifier
                    .padding(top = 32.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    IconButton(
                        onClick = {
                            selectedItem = BottomNavItem.HOME
                            onNavHome()
                        }
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            tint = if (selectedItem == BottomNavItem.HOME) Color.White else Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            selectedItem = BottomNavItem.LIBRARY
                            onNavLibrary()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_library_books_24),
                            contentDescription = "Library",
                            tint = if (selectedItem == BottomNavItem.LIBRARY) Color.White else Color.Gray,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    // Empty space for FAB (invisible spacer to maintain layout balance)
                    Spacer(modifier = Modifier.width(64.dp))
                    IconButton(
                        onClick = {
                            selectedItem = BottomNavItem.LEADERBOARD
                            onNavLeaderboard()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_equalizer_24),
                            contentDescription = "Leaderboard",
                            tint = if (selectedItem == BottomNavItem.LEADERBOARD) Color.White else Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            selectedItem = BottomNavItem.ME
                            onNavMe()
                        }
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Me",
                            tint = if (selectedItem == BottomNavItem.ME) Color.White else Color.Gray,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.linearGradient(
                            listOf(Purple1, Purple2)
                        )
                    )
            ) {
                // decorative circles
                Canvas(modifier = Modifier.matchParentSize()) {
                    val w = size.width
                    val h = size.height
                    drawCircle(
                        color = Color.White.copy(alpha = 0.08f),
                        radius = w * 0.45f,
                        center = Offset(x = w * 0.8f, y = -h * 0.1f)
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = 0.06f),
                        radius = w * 0.6f,
                        center = Offset(x = w * 0.2f, y = h * 0.2f)
                    )
                }

                Column(modifier = Modifier.padding(start = 20.dp, top = 56.dp)) {
                    Text(
                        text = "Hello Guest!",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "We are happy to have you back",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 16.sp
                    )
                }

                IconButton(
                    onClick = { /* trophy */ },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 16.dp, top = 16.dp)
                        .size(42.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_trophy_24),
                        contentDescription = "profile",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-28).dp) // lift up to overlap header
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(top = 18.dp, bottom = 18.dp, start = 14.dp, end = 14.dp)
            ) {
                // Reminder card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = SoftYellow)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Notifications,
                                    contentDescription = "Reminder",
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Reminder",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "XYZ's Quiz is Live Now!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = onJoinQuiz,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.width(110.dp),
                                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                            ) {
                                Text("Join Quiz", color = Color.Black)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.reminder),
                                contentDescription = "Reminder",
                                modifier = Modifier.size(140.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Divider(
                    color = Color.Gray,
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Weekly progress card
                Text("This Week's Progress", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(5.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F0FF))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("My Ranking: 17", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Total Scored earned: 149")
                            Text("Quizzes attempted: 7")
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // coin
                                Box(
                                    modifier = Modifier.size(35.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.coin),
                                        contentDescription = "Reminder",
                                        modifier = Modifier.size(140.dp),
                                        tint = Color.Unspecified
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("125", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        // Circular progress with percentage number inside
                        Box(
                            modifier = Modifier.width(110.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                CircularPercentage(percentage = 0.65f, label = "65%")
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Level:1",
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Daily challenges card
                Text("Daily Challenges", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(5.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(9.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF7E8EB))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.",
                                fontSize = 15.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { /* start */ },
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Get Started")
                            }
                        }

                        Box(
                            modifier = Modifier.padding(bottom = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.daily_challenge),
                                contentDescription = "Daily Challenge",
                                modifier = Modifier.size(150.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Earn rewards card
                Text("Earn Rewards", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(5.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(9.dp),
                    colors = CardDefaults.cardColors(containerColor = Purple2.copy(alpha = 0.85f))
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height
                        drawCircle(
                            color = Color.White.copy(alpha = 0.08f),
                            radius = w * 0.25f,
                            center = Offset(x = w * 0.9f, y = h * 0.9f)
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = 0.06f),
                            radius = w * 0.35f,
                            center = Offset(x = w * 0.6f, y = h * 0.2f)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.",
                                fontSize = 15.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { /* start */ },
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Invite")
                            }
                        }

                        Spacer(modifier = Modifier.width(5.dp))
                        Box(
                            modifier = Modifier.padding(bottom = 2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.coin),
                                contentDescription = "Coin",
                                modifier = Modifier.size(100.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CircularPercentage(
    percentage: Float,
    label: String,
    sizeDp: Dp = 100.dp
) {
    val sweep = percentage.coerceIn(0f, 1f) * 360f

    Box(modifier = Modifier.size(sizeDp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val stroke = Stroke(width = 10f, cap = StrokeCap.Round)
            // background ring
            drawArc(
                color = Color.LightGray.copy(alpha = 0.4f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )
            // active arc
            drawArc(
                color = Color(0xFF3CB371),
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                style = stroke
            )
        }

        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}

@Preview
@Composable
fun HomeScreenPreview2() {
    MaterialTheme {
        HomeScreenUi(onJoinQuiz = {}, onFabClick = {}, performance = "65%")
    }
}