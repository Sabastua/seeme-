package com.example.seeme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Translate
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AIDubbingDialog(
    onDismiss: () -> Unit,
    onApplyDubbing: (String, String) -> Unit
) {
    var selectedLanguage by remember { mutableStateOf("English") }
    var selectedBeat by remember { mutableStateOf("Lo-Fi Chill") }
    val haptic = LocalHapticFeedback.current

    val languages = listOf("English", "Swahili", "Japanese", "Portuguese", "Spanish", "French")
    val beats = listOf("Lo-Fi Chill", "Cyberpunk Synth", "Acoustic Melodic", "Afrobeat Rhythm")

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
                    Icon(imageVector = Icons.Filled.Translate, contentDescription = null, tint = InstagramPink, modifier = Modifier.size(26.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("AI Voice Dubbing & Beats", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InstagramTextDark)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = InstagramTextSecondary)
                }
            }

            // Language Selector
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Target Dubbing Language", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = InstagramTextDark)

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(languages) { lang ->
                        val isSelected = selectedLanguage == lang
                        Box(
                            modifier = Modifier
                                .shadow(if (isSelected) 3.dp else 1.dp, CircleShape)
                                .background(if (isSelected) InstagramPink else SmokeWhiteSurface, CircleShape)
                                .border(1.dp, if (isSelected) InstagramPink else SmokeWhiteBorder, CircleShape)
                                .clickable {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    selectedLanguage = lang
                                }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(lang, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = if (isSelected) Color.White else InstagramTextDark)
                        }
                    }
                }
            }

            // AI Beats Selector
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Background Music Beat", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = InstagramTextDark)

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(beats) { beat ->
                        val isSelected = selectedBeat == beat
                        Box(
                            modifier = Modifier
                                .shadow(if (isSelected) 3.dp else 1.dp, CircleShape)
                                .background(if (isSelected) InstagramTextDark else SmokeWhiteSurface, CircleShape)
                                .border(1.dp, if (isSelected) InstagramTextDark else SmokeWhiteBorder, CircleShape)
                                .clickable {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    selectedBeat = beat
                                }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(beat, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = if (isSelected) Color.White else InstagramTextDark)
                        }
                    }
                }
            }

            // Apply Button
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onApplyDubbing(selectedLanguage, selectedBeat)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(4.dp, CircleShape),
                colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                shape = CircleShape
            ) {
                Text("Generate AI Voice & Music", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
            }
        }
    }
}
