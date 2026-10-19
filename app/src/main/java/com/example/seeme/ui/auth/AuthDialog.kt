package com.example.seeme.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthDialog(
    onDismiss: () -> Unit,
    onSignUpCreator: (String, String, String, String, String, String) -> Unit,
    onSignIn: (String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Sign Up, 1 = Sign In
    val haptic = LocalHapticFeedback.current

    // Sign Up State
    var name by remember { mutableStateOf("") }
    var handle by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedCountryFlag by remember { mutableStateOf("🇰🇪") }
    var selectedCategory by remember { mutableStateOf("Coding") }

    val countries = listOf("🇰🇪 Kenya", "🇺🇸 USA", "🇳🇬 Nigeria", "🇬🇧 UK", "🇯🇵 Japan", "🇧🇷 Brazil")
    val categories = listOf("Coding", "Tech", "Gaming", "Music", "Art", "Fitness")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SmokeWhiteSurface,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .background(InstagramPink, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Filled.Videocam, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Creator Sign Up Gate", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = InstagramTextDark)
                            Text("Required to start broadcasting live", fontSize = 12.sp, color = InstagramTextSecondary)
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = InstagramTextSecondary)
                    }
                }
            }

            // Tab Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SmokeWhiteCard, CircleShape)
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(if (selectedTab == 0) 2.dp else 0.dp, CircleShape)
                            .background(if (selectedTab == 0) SmokeWhiteSurface else Color.Transparent, CircleShape)
                            .clickable { selectedTab = 0 }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Sign Up (Creator)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = if (selectedTab == 0) InstagramPink else InstagramTextSecondary)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(if (selectedTab == 1) 2.dp else 0.dp, CircleShape)
                            .background(if (selectedTab == 1) SmokeWhiteSurface else Color.Transparent, CircleShape)
                            .clickable { selectedTab = 1 }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Sign In", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = if (selectedTab == 1) InstagramPink else InstagramTextSecondary)
                    }
                }
            }

            if (selectedTab == 0) {
                // SIGN UP FORM
                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Full Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(focusedContainerColor = SmokeWhiteSurface, unfocusedContainerColor = SmokeWhiteSurface)
                    )
                }

                item {
                    OutlinedTextField(
                        value = handle,
                        onValueChange = { handle = it },
                        label = { Text("Creator Handle (e.g. @my_handle)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(focusedContainerColor = SmokeWhiteSurface, unfocusedContainerColor = SmokeWhiteSurface)
                    )
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Select Country", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = InstagramTextSecondary)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(countries) { cty ->
                                val flag = cty.take(2)
                                val isSelected = selectedCountryFlag == flag
                                Box(
                                    modifier = Modifier
                                        .background(if (isSelected) InstagramPink else SmokeWhiteSurface, CircleShape)
                                        .border(1.dp, if (isSelected) InstagramPink else SmokeWhiteBorder, CircleShape)
                                        .clickable { selectedCountryFlag = flag }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(cty, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (isSelected) Color.White else InstagramTextDark)
                                }
                            }
                        }
                    }
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Creator Category", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = InstagramTextSecondary)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(categories) { cat ->
                                val isSelected = selectedCategory == cat
                                Box(
                                    modifier = Modifier
                                        .background(if (isSelected) InstagramTextDark else SmokeWhiteSurface, CircleShape)
                                        .border(1.dp, if (isSelected) InstagramTextDark else SmokeWhiteBorder, CircleShape)
                                        .clickable { selectedCategory = cat }
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(cat, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = if (isSelected) Color.White else InstagramTextDark)
                                }
                            }
                        }
                    }
                }

                item {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(focusedContainerColor = SmokeWhiteSurface, unfocusedContainerColor = SmokeWhiteSurface)
                    )
                }

                item {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(focusedContainerColor = SmokeWhiteSurface, unfocusedContainerColor = SmokeWhiteSurface)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onSignUpCreator(name, handle, email, password, selectedCountryFlag, selectedCategory)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(4.dp, CircleShape),
                        colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                        shape = CircleShape
                    ) {
                        Text("Create Creator Account & Go Live 🚀", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                    }
                }
            } else {
                // SIGN IN FORM
                item {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(focusedContainerColor = SmokeWhiteSurface, unfocusedContainerColor = SmokeWhiteSurface)
                    )
                }

                item {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(focusedContainerColor = SmokeWhiteSurface, unfocusedContainerColor = SmokeWhiteSurface)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onSignIn(email)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .shadow(4.dp, CircleShape),
                        colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                        shape = CircleShape
                    ) {
                        Text("Sign In & Continue to Broadcast", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
