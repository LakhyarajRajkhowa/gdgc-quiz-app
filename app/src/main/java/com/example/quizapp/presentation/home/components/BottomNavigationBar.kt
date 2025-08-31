package com.example.quizapp.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.quizapp.R
import com.example.quizapp.presentation.navigation.BottomNavItem

@Composable
fun BottomNavigationBar(
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit
) {
    Surface(
        tonalElevation = 8.dp,
        color = Color(0xFF6A3DF0), // Purple2
        modifier = Modifier.padding(top = 32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 8.dp),

            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(onClick = { onItemSelected(BottomNavItem.HOME) }) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (selectedItem == BottomNavItem.HOME) Color.White else Color.Gray,
                    modifier = Modifier.size(32.dp)

                )
            }
            IconButton(onClick = { onItemSelected(BottomNavItem.LIBRARY) }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_library_books_24),
                    contentDescription = "Library",
                    tint = if (selectedItem == BottomNavItem.LIBRARY) Color.White else Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(64.dp)) // space for FAB
            IconButton(onClick = { onItemSelected(BottomNavItem.LEADERBOARD) }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_equalizer_24),
                    contentDescription = "Leaderboard",
                    tint = if (selectedItem == BottomNavItem.LEADERBOARD) Color.White else Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(onClick = { onItemSelected(BottomNavItem.ME) }) {
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
