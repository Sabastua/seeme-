package com.example.seeme.ui.dm

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.data.model.CreatorProfile
import com.example.seeme.ui.theme.*

data class DirectMessage(
    val id: String,
    val senderName: String,
    val message: String,
    val isFromMe: Boolean,
    val time: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectMessageScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedThreadCreator by remember { mutableStateOf<String?>(null) }
    var chatMessage by remember { mutableStateOf("") }

    val messages = remember {
        mutableStateListOf(
            DirectMessage("m1", "Alex Riviera", "Hey! Thanks for watching the stream today! 🚀", false, "10:14 AM"),
            DirectMessage("m2", "You", "Loved the Jetpack Compose animations!", true, "10:15 AM"),
            DirectMessage("m3", "Alex Riviera", "Appreciate it! See you on the next stream!", false, "10:16 AM")
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = selectedThreadCreator ?: "Direct Messages",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = InstagramTextDark
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (selectedThreadCreator != null) selectedThreadCreator = null else onBack()
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = InstagramTextDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SmokeWhiteSurface)
            )
        },
        containerColor = SmokeWhiteBg,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        if (selectedThreadCreator == null) {
            // INBOX THREADS LIST
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text("Inbox (3)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = InstagramTextDark)
                }

                items(listOf("Alex Riviera 🇺🇸", "Amina Wanjiku 🇰🇪", "Kenji Sato 🇯🇵")) { name ->
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(3.dp, RoundedCornerShape(18.dp))
                            .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(18.dp))
                            .clickable { selectedThreadCreator = name }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(
                                        brush = Brush.linearGradient(listOf(InstagramPink, InstagramGradientEnd)),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(name.take(1).uppercase(), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = InstagramTextDark)
                                Text("Hey! Thanks for watching the stream!", fontSize = 12.sp, color = InstagramTextSecondary, maxLines = 1)
                            }

                            Text("10:16 AM", fontSize = 11.sp, color = InstagramTextSecondary)
                        }
                    }
                }
            }
        } else {
            // 1-ON-1 CHAT THREAD VIEW
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(messages) { msg ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (msg.isFromMe) Arrangement.End else Arrangement.Start
                        ) {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (msg.isFromMe) InstagramPink else SmokeWhiteSurface
                                ),
                                modifier = Modifier.widthIn(max = 260.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(msg.message, color = if (msg.isFromMe) Color.White else InstagramTextDark, fontSize = 14.sp)
                                    Text(msg.time, color = if (msg.isFromMe) Color.White.copy(0.7f) else InstagramTextSecondary, fontSize = 10.sp, modifier = Modifier.align(Alignment.End))
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = chatMessage,
                        onValueChange = { chatMessage = it },
                        placeholder = { Text("Send private message...") },
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(focusedContainerColor = SmokeWhiteSurface, unfocusedContainerColor = SmokeWhiteSurface)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (chatMessage.isNotBlank()) {
                                messages.add(DirectMessage(System.currentTimeMillis().toString(), "You", chatMessage, true, "Now"))
                                chatMessage = ""
                            }
                        },
                        modifier = Modifier
                            .background(InstagramPink, CircleShape)
                            .size(48.dp)
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White)
                    }
                }
            }
        }
    }
}
