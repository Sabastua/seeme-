package com.example.seeme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cast
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.ui.theme.*

data class CastDevice(
    val id: String,
    val name: String,
    val type: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastDialog(
    onDismiss: () -> Unit
) {
    var connectedDeviceId by remember { mutableStateOf<String?>(null) }
    val haptic = LocalHapticFeedback.current

    val devices = listOf(
        CastDevice("d1", "Living Room Google TV", "Chromecast Built-in"),
        CastDevice("d2", "Studio Samsung QLED", "Tizen OS"),
        CastDevice("d3", "Office LG OLED", "webOS"),
        CastDevice("d4", "Bedroom Apple TV 4K", "AirPlay / Cast")
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SmokeWhiteSurface,
        scrimColor = Color.Black.copy(alpha = 0.45f),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.Cast, contentDescription = null, tint = InstagramPink, modifier = Modifier.size(26.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cast Live Stream to Smart TV", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InstagramTextDark)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = InstagramTextSecondary)
                }
            }

            Text("Select Nearby Display Device", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = InstagramTextDark)

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                items(devices, key = { it.id }) { dev ->
                    val isConnected = connectedDeviceId == dev.id

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = if (isConnected) SmokeWhiteCard else SmokeWhiteSurface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(if (isConnected) 4.dp else 1.dp, RoundedCornerShape(16.dp))
                            .border(
                                width = if (isConnected) 2.dp else 1.dp,
                                color = if (isConnected) InstagramPink else SmokeWhiteBorder,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                connectedDeviceId = if (isConnected) null else dev.id
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .background(if (isConnected) InstagramPink.copy(0.12f) else SmokeWhiteCard, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Filled.Tv, contentDescription = null, tint = if (isConnected) InstagramPink else InstagramTextDark)
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(dev.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = InstagramTextDark)
                                    Text(dev.type, fontSize = 11.sp, color = InstagramTextSecondary)
                                }
                            }

                            Button(
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    connectedDeviceId = if (isConnected) null else dev.id
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isConnected) Color(0xFF2E7D32) else InstagramPink
                                ),
                                shape = CircleShape,
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text(if (isConnected) "Connected ✓" else "Cast", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
