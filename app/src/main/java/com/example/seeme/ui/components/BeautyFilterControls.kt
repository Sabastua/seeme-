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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
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
fun BeautyFilterControls(
    onDismiss: () -> Unit
) {
    var skinSmoothness by remember { mutableFloatStateOf(0.7f) }
    var portraitGlow by remember { mutableFloatStateOf(0.5f) }
    var selectedBackground by remember { mutableStateOf("Bokeh Blur") }
    val haptic = LocalHapticFeedback.current

    val backgrounds = listOf("None", "Bokeh Blur", "Neon Studio", "Sunset Beach", "Minimal White")

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
                    Icon(imageVector = Icons.Filled.AutoAwesome, contentDescription = null, tint = InstagramPink, modifier = Modifier.size(26.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("AR Beauty & Background Effects", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InstagramTextDark)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = InstagramTextSecondary)
                }
            }

            // Skin Smoothness Slider
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Skin Smoothness", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = InstagramTextDark)
                    Text("${(skinSmoothness * 100).toInt()}%", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = InstagramPink)
                }
                Slider(
                    value = skinSmoothness,
                    onValueChange = {
                        skinSmoothness = it
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    },
                    colors = SliderDefaults.colors(thumbColor = InstagramPink, activeTrackColor = InstagramPink)
                )
            }

            // Portrait Glow Slider
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Portrait Glow & Lighting", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = InstagramTextDark)
                    Text("${(portraitGlow * 100).toInt()}%", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = CoinGoldDark)
                }
                Slider(
                    value = portraitGlow,
                    onValueChange = {
                        portraitGlow = it
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    },
                    colors = SliderDefaults.colors(thumbColor = CoinGold, activeTrackColor = CoinGold)
                )
            }

            // Virtual Backgrounds
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Virtual Studio Background", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = InstagramTextDark)

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(backgrounds) { bg ->
                        val isSelected = selectedBackground == bg
                        Box(
                            modifier = Modifier
                                .shadow(if (isSelected) 3.dp else 1.dp, CircleShape)
                                .background(if (isSelected) InstagramPink else SmokeWhiteCard, CircleShape)
                                .border(1.dp, if (isSelected) InstagramPink else SmokeWhiteBorder, CircleShape)
                                .clickable {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    selectedBackground = bg
                                }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(bg, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (isSelected) Color.White else InstagramTextDark)
                        }
                    }
                }
            }

            // Done Button
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .shadow(4.dp, CircleShape),
                colors = ButtonDefaults.buttonColors(containerColor = InstagramTextDark),
                shape = CircleShape
            ) {
                Text("Apply Beauty Effects ✓", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
            }
        }
    }
}
