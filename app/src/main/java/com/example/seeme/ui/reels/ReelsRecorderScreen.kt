package com.example.seeme.ui.reels

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.ui.components.CameraPreview
import kotlinx.coroutines.delay

@Composable
fun ReelsRecorderScreen(
    onClose: () -> Unit,
    onPostReel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isRecording by remember { mutableStateOf(false) }
    var recordTimeSeconds by remember { mutableIntStateOf(0) }
    var selectedSpeed by remember { mutableStateOf("1.0x") }
    var maxDurationSeconds by remember { mutableIntStateOf(15) }
    val haptic = LocalHapticFeedback.current

    val speeds = listOf("0.5x", "1.0x", "2.0x")

    LaunchedEffect(isRecording) {
        if (isRecording) {
            recordTimeSeconds = 0
            while (isRecording && recordTimeSeconds < maxDurationSeconds) {
                delay(1000)
                recordTimeSeconds++
            }
            if (recordTimeSeconds >= maxDurationSeconds) {
                isRecording = false
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Camera Preview
        CameraPreview(
            isFrontCamera = true,
            modifier = Modifier.fillMaxSize()
        )

        // Recorder Overlay Controls
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Music Picker Pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(imageVector = Icons.Filled.MusicNote, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Add Music Sound 🎵", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        .size(40.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = Color.White)
                }
            }

            // Speed Selector Column (Right Side)
            Column(
                modifier = Modifier
                    .align(Alignment.End)
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .padding(6.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                speeds.forEach { speed ->
                    val isSelected = selectedSpeed == speed
                    Box(
                        modifier = Modifier
                            .background(if (isSelected) Color.White else Color.Transparent, CircleShape)
                            .clickable { selectedSpeed = speed }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(speed, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = if (isSelected) Color.Black else Color.White)
                    }
                }
            }

            // Bottom Recording Controls
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Timer
                Text(
                    text = String.format("00:%02d / 00:%02d", recordTimeSeconds, maxDurationSeconds),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                // Record Button
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .border(4.dp, if (isRecording) Color.Red else Color.White, CircleShape)
                        .padding(8.dp)
                        .background(if (isRecording) Color.Red else Color.White, CircleShape)
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            isRecording = !isRecording
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (isRecording) {
                        Box(modifier = Modifier.size(24.dp).background(Color.White, RoundedCornerShape(4.dp)))
                    }
                }

                // Post Button
                if (recordTimeSeconds > 0 && !isRecording) {
                    Button(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onPostReel()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = CircleShape
                    ) {
                        Icon(imageVector = Icons.Filled.Send, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Post Short Reel to Profile 🚀", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
