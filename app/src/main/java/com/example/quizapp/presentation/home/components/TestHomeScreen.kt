//package com.example.quizapp.presentation.home
//
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.KeyboardArrowDown
//import androidx.compose.material.icons.filled.KeyboardArrowRight
//import androidx.compose.material.icons.filled.Notifications
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.RectangleShape
//import androidx.compose.ui.graphics.StrokeCap
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.graphics.vector.VectorPainter
//import androidx.compose.ui.graphics.vector.rememberVectorPainter
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.window.DialogProperties
//import com.example.quizapp.R
//import com.example.quizapp.presentation.auth.RegisterStep
//import kotlinx.coroutines.launch
//import androidx.compose.ui.window.Dialog
//import com.example.quizapp.presentation.quiz.CreateQuizCodeDialog
//
//private val Purple1 = Color(0xFF7D4CFF)
//private val Purple2 = Color(0xFF6A3DF0)
//private val SoftYellow = Color(0xFFF6C66B)
//
//enum class BottomNavItem {
//    HOME, LIBRARY, LEADERBOARD, ME
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HomeScreenUi(
//    onJoinQuiz: (String) -> Unit = {},
//    onFabClick: () -> Unit = {},
//    onNavHome: () -> Unit = {},
//    onNavLibrary: () -> Unit = {},
//    onNavLeaderboard: () -> Unit = {},
//    onNavMe: () -> Unit = {},
//    performance: String = "65%"
//) {
//    var selectedItem by remember { mutableStateOf(BottomNavItem.HOME) }
//    val sheetState = rememberModalBottomSheetState()
//    val scope = rememberCoroutineScope()
//    var showBottomSheet by remember { mutableStateOf(false) }
//    var showJoinDialog by remember { mutableStateOf(false) }
//    var showCreateQuizCodeDialog by remember { mutableStateOf(false) }
//
//
//    if (showBottomSheet) {
//        ModalBottomSheet(
//            onDismissRequest = { showBottomSheet = false },
//            sheetState = sheetState
//        ) {
//            QuizOptionsBottomSheet(
//                onCreateQuiz = {
//                    scope.launch { sheetState.hide() }.invokeOnCompletion {
//                        showBottomSheet = false
//                        // Handle create quiz action here
//                    }
//                },
//                onJoinQuiz = {
//                    scope.launch { sheetState.hide() }.invokeOnCompletion {
//                        showBottomSheet = false
//                        showJoinDialog = true
//                    }
//                }
//            )
//        }
//    }
//
//    if (showJoinDialog) {
//        JoinQuizDialog(
//            onDismiss = { showJoinDialog = false },
//            onJoinQuiz = { code ->
//                showJoinDialog = false
//                onJoinQuiz(code)  // Pass the join code to the parent
//            }
//        )
//    }
//
//    if (showCreateQuizCodeDialog) {
//        CreateQuizCodeDialog(
//            code = "8YLCZP",
//            onDismiss = { showCreateQuizCodeDialog = false },
//            onCopy = { /* copy logic */ },
//            onShare = { /* share logic */ }
//        )
//    }
//
//
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { showBottomSheet = true },
//                containerColor = Purple1,
//                contentColor = Color.White,
//                modifier = Modifier
//                    .size(64.dp)
//                    .offset(y = (74).dp),
//                shape = CircleShape
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Add,
//                    contentDescription = null,
//                    modifier = Modifier.size(32.dp)
//                )
//            }
//        },
//        floatingActionButtonPosition = FabPosition.Center,
//        bottomBar = {
//            Surface(
//                tonalElevation = 8.dp,
//                color = Purple2,
//                modifier = Modifier
//                    .padding(top = 32.dp)
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(60.dp)
//                        .padding(horizontal = 8.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceAround
//                ) {
//                    IconButton(
//                        onClick = {
//                            selectedItem = BottomNavItem.HOME
//                            onNavHome()
//                        }
//                    ) {
//                        Icon(
//                            Icons.Default.Home,
//                            contentDescription = "Home",
//                            tint = if (selectedItem == BottomNavItem.HOME) Color.White else Color.Gray,
//                            modifier = Modifier.size(32.dp)
//                        )
//                    }
//                    IconButton(
//                        onClick = {
//                            selectedItem = BottomNavItem.LIBRARY
//                            onNavLibrary()
//                        }
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.baseline_library_books_24),
//                            contentDescription = "Library",
//                            tint = if (selectedItem == BottomNavItem.LIBRARY) Color.White else Color.Gray,
//                            modifier = Modifier.size(30.dp)
//                        )
//                    }
//                    // Empty space for FAB (invisible spacer to maintain layout balance)
//                    Spacer(modifier = Modifier.width(64.dp))
//                    IconButton(
//                        onClick = {
//                            selectedItem = BottomNavItem.LEADERBOARD
//                            onNavLeaderboard()
//                        }
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.outline_equalizer_24),
//                            contentDescription = "Leaderboard",
//                            tint = if (selectedItem == BottomNavItem.LEADERBOARD) Color.White else Color.Gray,
//                            modifier = Modifier.size(32.dp)
//                        )
//                    }
//                    IconButton(
//                        onClick = {
//                            selectedItem = BottomNavItem.ME
//                            onNavMe()
//                        }
//                    ) {
//                        Icon(
//                            Icons.Default.Person,
//                            contentDescription = "Me",
//                            tint = if (selectedItem == BottomNavItem.ME) Color.White else Color.Gray,
//                            modifier = Modifier.size(32.dp)
//                        )
//                    }
//                }
//            }
//        }
//    ) { innerPadding ->
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .verticalScroll(rememberScrollState())
//        ) {
//            // Header
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(220.dp)
//                    .background(
//                        brush = Brush.linearGradient(
//                            listOf(Purple1, Purple2)
//                        )
//                    )
//            ) {
//                // decorative circles
//                Canvas(modifier = Modifier.matchParentSize()) {
//                    val w = size.width
//                    val h = size.height
//                    drawCircle(
//                        color = Color.White.copy(alpha = 0.08f),
//                        radius = w * 0.45f,
//                        center = Offset(x = w * 0.8f, y = -h * 0.1f)
//                    )
//                    drawCircle(
//                        color = Color.White.copy(alpha = 0.06f),
//                        radius = w * 0.6f,
//                        center = Offset(x = w * 0.2f, y = h * 0.2f)
//                    )
//                }
//
//                Column(modifier = Modifier.padding(start = 20.dp, top = 56.dp)) {
//                    Text(
//                        text = "Hello Guest!",
//                        color = Color.White,
//                        fontSize = 32.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Spacer(modifier = Modifier.height(6.dp))
//                    Text(
//                        text = "We are happy to have you back",
//                        color = Color.White.copy(alpha = 0.9f),
//                        fontSize = 16.sp
//                    )
//                }
//
//                IconButton(
//                    onClick = { /* trophy */ },
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .padding(end = 16.dp, top = 16.dp)
//                        .size(42.dp)
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.outline_trophy_24),
//                        contentDescription = "profile",
//                        tint = Color.White,
//                        modifier = Modifier.size(32.dp)
//                    )
//                }
//            }
//
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .offset(y = (-28).dp) // lift up to overlap header
//                    .clip(RoundedCornerShape(12.dp))
//                    .background(Color.White)
//                    .padding(top = 18.dp, bottom = 18.dp, start = 14.dp, end = 14.dp)
//            ) {
//                // Reminder card
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(150.dp),
//                    shape = RoundedCornerShape(12.dp),
//                    colors = CardDefaults.cardColors(containerColor = SoftYellow)
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(12.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Column(modifier = Modifier.weight(1f)) {
//                            Row(verticalAlignment = Alignment.CenterVertically) {
//                                Icon(
//                                    Icons.Default.Notifications,
//                                    contentDescription = "Reminder",
//                                    tint = Color.Black
//                                )
//                                Spacer(modifier = Modifier.width(8.dp))
//                                Text(
//                                    text = "Reminder",
//                                    fontSize = 16.sp,
//                                    fontWeight = FontWeight.Medium
//                                )
//                            }
//                            Spacer(modifier = Modifier.height(6.dp))
//                            Text(
//                                text = "XYZ's Quiz is Live Now!",
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Bold
//                            )
//                            Spacer(modifier = Modifier.height(8.dp))
//                            OutlinedButton(
//                                onClick = { showJoinDialog = true },
//                                shape = RoundedCornerShape(16.dp),
//                                modifier = Modifier.width(110.dp),
//                                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
//                            ) {
//                                Text("Join Quiz", color = Color.Black)
//                            }
//                        }
//
//                        Box(
//                            modifier = Modifier
//                                .size(100.dp)
//                                .padding(2.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.reminder),
//                                contentDescription = "Reminder",
//                                modifier = Modifier.size(140.dp),
//                                tint = Color.Unspecified
//                            )
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Divider(
//                    color = Color.Gray,
//                    thickness = 1.dp
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Weekly progress card
//                Text("This Week's Progress", fontWeight = FontWeight.SemiBold)
//                Spacer(modifier = Modifier.height(5.dp))
//
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(12.dp),
//                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F0FF))
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Column(modifier = Modifier.weight(1f)) {
//                            Text("My Ranking: 17", fontWeight = FontWeight.Bold)
//                            Spacer(modifier = Modifier.height(6.dp))
//                            Text("Total Scored earned: 149")
//                            Text("Quizzes attempted: 7")
//                            Spacer(modifier = Modifier.height(8.dp))
//                            Row(verticalAlignment = Alignment.CenterVertically) {
//                                // coin
//                                Box(
//                                    modifier = Modifier.size(35.dp),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    Icon(
//                                        painter = painterResource(id = R.drawable.coin),
//                                        contentDescription = "Reminder",
//                                        modifier = Modifier.size(140.dp),
//                                        tint = Color.Unspecified
//                                    )
//                                }
//                                Spacer(modifier = Modifier.width(8.dp))
//                                Text("125", fontSize = 15.sp, fontWeight = FontWeight.Bold)
//                            }
//                        }
//
//                        // Circular progress with percentage number inside
//                        Box(
//                            modifier = Modifier.width(110.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Column {
//                                CircularPercentage(percentage = 0.65f, label = "65%")
//                                Spacer(modifier = Modifier.height(8.dp))
//                                Text(
//                                    text = "Level:1",
//                                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                                )
//                            }
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Daily challenges card
//                Text("Daily Challenges", fontWeight = FontWeight.SemiBold)
//                Spacer(modifier = Modifier.height(5.dp))
//
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(9.dp),
//                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF7E8EB))
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Column(modifier = Modifier.weight(1f)) {
//                            Text(
//                                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.",
//                                fontSize = 15.sp,
//                                maxLines = 3,
//                                overflow = TextOverflow.Ellipsis
//                            )
//                            Spacer(modifier = Modifier.height(12.dp))
//                            Button(
//                                onClick = { /* start */ },
//                                shape = RoundedCornerShape(20.dp),
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = Color.Black,
//                                    contentColor = Color.White
//                                )
//                            ) {
//                                Text("Get Started")
//                            }
//                        }
//
//                        Box(
//                            modifier = Modifier.padding(bottom = 2.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.daily_challenge),
//                                contentDescription = "Daily Challenge",
//                                modifier = Modifier.size(150.dp),
//                                tint = Color.Unspecified
//                            )
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Earn rewards card
//                Text("Earn Rewards", fontWeight = FontWeight.SemiBold)
//                Spacer(modifier = Modifier.height(5.dp))
//
//                Card(
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(9.dp),
//                    colors = CardDefaults.cardColors(containerColor = Purple2.copy(alpha = 0.85f))
//                ) {
//                    Canvas(modifier = Modifier.fillMaxSize()) {
//                        val w = size.width
//                        val h = size.height
//                        drawCircle(
//                            color = Color.White.copy(alpha = 0.08f),
//                            radius = w * 0.25f,
//                            center = Offset(x = w * 0.9f, y = h * 0.9f)
//                        )
//                        drawCircle(
//                            color = Color.White.copy(alpha = 0.06f),
//                            radius = w * 0.35f,
//                            center = Offset(x = w * 0.6f, y = h * 0.2f)
//                        )
//                    }
//
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Column(modifier = Modifier.weight(1f)) {
//                            Text(
//                                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor.",
//                                fontSize = 15.sp,
//                                maxLines = 3,
//                                overflow = TextOverflow.Ellipsis,
//                                color = Color.White
//                            )
//                            Spacer(modifier = Modifier.height(12.dp))
//                            Button(
//                                onClick = { /* start */ },
//                                shape = RoundedCornerShape(20.dp),
//                                colors = ButtonDefaults.buttonColors(
//                                    containerColor = Color.Black,
//                                    contentColor = Color.White
//                                )
//                            ) {
//                                Text("Invite")
//                            }
//                        }
//
//                        Spacer(modifier = Modifier.width(5.dp))
//                        Box(
//                            modifier = Modifier.padding(bottom = 2.dp),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.coin),
//                                contentDescription = "Coin",
//                                modifier = Modifier.size(100.dp),
//                                tint = Color.Unspecified
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun CircularPercentage(
//    percentage: Float,
//    label: String,
//    sizeDp: Dp = 100.dp
//) {
//    val sweep = percentage.coerceIn(0f, 1f) * 360f
//
//    Box(modifier = Modifier.size(sizeDp), contentAlignment = Alignment.Center) {
//        Canvas(modifier = Modifier.matchParentSize()) {
//            val stroke = Stroke(width = 10f, cap = StrokeCap.Round)
//            // background ring
//            drawArc(
//                color = Color.LightGray.copy(alpha = 0.4f),
//                startAngle = -90f,
//                sweepAngle = 360f,
//                useCenter = false,
//                style = stroke
//            )
//            // active arc
//            drawArc(
//                color = Color(0xFF3CB371),
//                startAngle = -90f,
//                sweepAngle = sweep,
//                useCenter = false,
//                style = stroke
//            )
//        }
//
//        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 18.sp)
//    }
//}
//
//@Preview
//@Composable
//fun HomeScreenPreview() {
//    MaterialTheme {
//        HomeScreenUi(onJoinQuiz = {}, onFabClick = {}, performance = "65%")
//    }
//}
//
//@Composable
//fun QuizOptionsBottomSheet(
//    onCreateQuiz: () -> Unit = {},
//    onJoinQuiz: () -> Unit = {}
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White)
//            .padding(
//                start = 10.dp,
//                top = 25.dp,
//                end = 10.dp
//            )
//    ) {
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Button(
//                onClick = onCreateQuiz,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(60.dp)
//                    .padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
//                shape = RoundedCornerShape(8.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFF7D4CFF),
//                    contentColor = Color.White
//                )
//            ) {
//                Text("Create Quiz", fontWeight = FontWeight.Bold, fontSize = 20.sp)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        Button(
//            onClick = onJoinQuiz,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp)
//                .padding(bottom = 20.dp, start = 20.dp, end = 20.dp),
//            shape = RoundedCornerShape(8.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color(0xFF7D4CFF),
//                contentColor = Color.White
//            )
//        ) {
//            Text("Join Quiz", fontWeight = FontWeight.Bold, fontSize = 20.sp)
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun JoinQuizDialog(
//    onDismiss: () -> Unit,
//    onJoinQuiz: (String) -> Unit
//) {
//    var joinCode by remember { mutableStateOf("") }
//
//    Dialog(
//        onDismissRequest = onDismiss,
//        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
//    ) {
//        JoinQuizDialogContent(
//            joinCode = joinCode,
//            onJoinCodeChange = { joinCode = it },
//            onDismiss = onDismiss,
//            onJoin = { onJoinQuiz(joinCode) }
//        )
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun JoinQuizDialogContent(
//    joinCode: String,
//    onJoinCodeChange: (String) -> Unit,
//    onDismiss: () -> Unit,
//    onJoin: () -> Unit
//) {
//    Surface(
//        shape = RoundedCornerShape(16.dp),
//        color = Color.White,
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(24.dp)
//                .fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Enter the joining code",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    color = Color.Black
//                )
//                IconButton(
//                    onClick = onDismiss,
//                    modifier = Modifier.size(24.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "Close",
//                        tint = Color.Gray
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(15.dp))
//
//            OutlinedTextField(
//                value = joinCode,
//                onValueChange = onJoinCodeChange,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp),
//                placeholder = {
//                    Text(
//                        text = "Enter code",
//                        color = Color.Gray,
//                        fontSize = 16.sp
//                    )
//                },
//                shape = RoundedCornerShape(12.dp),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.White,
//                    unfocusedContainerColor = Color.White,
//                    focusedIndicatorColor = Color.Black,
//                    unfocusedIndicatorColor = Color.LightGray,
//                    focusedTextColor = Color.Black,
//                    unfocusedTextColor = Color.Black
//                ),
//                textStyle = LocalTextStyle.current.copy(
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Medium
//                ),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//                singleLine = true
//            )
//
//            Spacer(modifier = Modifier.height(25.dp))
//
//            Button(
//                onClick = onJoin,
//                modifier = Modifier
//                    .wrapContentWidth()
//                    .height(56.dp),
//                shape = RoundedCornerShape(12.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFF7D4CFF),
//                    contentColor = Color.White
//                ),
//                enabled = joinCode.isNotBlank()
//            ) {
//                Text(
//                    text = "Join",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//    }
//}
//
////@Preview()
////@Composable
////fun JoinQuizDialogContentPreview() {
////    var code by remember { mutableStateOf("") }
////    MaterialTheme {
////        Box {
////            JoinQuizDialogContent(
////                joinCode = code,
////                onJoinCodeChange = { code = it },
////                onDismiss = {},
////                onJoin = {}
////            )
////        }
////    }
////}