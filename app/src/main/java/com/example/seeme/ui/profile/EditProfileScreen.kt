package com.example.seeme.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("Alex Riviera") }
    var username by remember { mutableStateOf("alex_riviera") }
    var category by remember { mutableStateOf("Video Creator & Live Streamer") }
    var bio by remember { mutableStateOf("Building social video apps with Jetpack Compose 🚀\nJoin my live streams every day!") }
    var website by remember { mutableStateOf("https://seeme.app/alex") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InstagramTextDark) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = InstagramTextDark)
                    }
                },
                actions = {
                    IconButton(onClick = onSave) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Save", tint = InstagramPink)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SmokeWhiteSurface)
            )
        },
        containerColor = SmokeWhiteBg,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Avatar Edit Ring
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(InstagramPink, CircleShape)
                    .padding(3.dp)
                    .background(SmokeWhiteSurface, CircleShape)
                    .border(1.dp, SmokeWhiteBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("AR", fontWeight = FontWeight.Bold, fontSize = 28.sp, color = InstagramPink)
            }

            TextButton(onClick = {}) {
                Text("Change Profile Photo", color = InstagramPink, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            // Form Fields
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SmokeWhiteSurface,
                    unfocusedContainerColor = SmokeWhiteSurface,
                    focusedTextColor = InstagramTextDark,
                    unfocusedTextColor = InstagramTextDark
                )
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SmokeWhiteSurface,
                    unfocusedContainerColor = SmokeWhiteSurface,
                    focusedTextColor = InstagramTextDark,
                    unfocusedTextColor = InstagramTextDark
                )
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SmokeWhiteSurface,
                    unfocusedContainerColor = SmokeWhiteSurface,
                    focusedTextColor = InstagramTextDark,
                    unfocusedTextColor = InstagramTextDark
                )
            )

            OutlinedTextField(
                value = bio,
                onValueChange = { bio = it },
                label = { Text("Bio") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                maxLines = 4,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SmokeWhiteSurface,
                    unfocusedContainerColor = SmokeWhiteSurface,
                    focusedTextColor = InstagramTextDark,
                    unfocusedTextColor = InstagramTextDark
                )
            )

            OutlinedTextField(
                value = website,
                onValueChange = { website = it },
                label = { Text("Website") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SmokeWhiteSurface,
                    unfocusedContainerColor = SmokeWhiteSurface,
                    focusedTextColor = InstagramTextDark,
                    unfocusedTextColor = InstagramTextDark
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(4.dp, CircleShape),
                colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                shape = CircleShape
            ) {
                Text("Save Profile Changes", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
