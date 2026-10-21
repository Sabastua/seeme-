package com.example.seeme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.data.model.LivePoll
import com.example.seeme.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LivePollDialog(
    poll: LivePoll,
    onDismiss: () -> Unit,
    onVoteOption: (String) -> Unit
) {
    var selectedOptionId by remember { mutableStateOf<String?>(null) }
    val haptic = LocalHapticFeedback.current

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
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.HowToVote, contentDescription = null, tint = InstagramPink, modifier = Modifier.size(26.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Interactive Live Poll", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InstagramTextDark)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = InstagramTextSecondary)
                }
            }

            // Question Card
            Card(
                colors = CardDefaults.cardColors(containerColor = SmokeWhiteCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(poll.question, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = InstagramTextDark)
                    Text("📊 ${poll.totalVotes} total votes cast", fontSize = 12.sp, color = InstagramTextSecondary)
                }
            }

            // Options List with Progress Bars
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                poll.options.forEach { opt ->
                    val isVoted = selectedOptionId == opt.id
                    val percent = (opt.votes.toFloat() / poll.totalVotes.toFloat()) * 100f

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = if (isVoted) SmokeWhiteCard else SmokeWhiteSurface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(if (isVoted) 3.dp else 1.dp, RoundedCornerShape(14.dp))
                            .border(
                                width = if (isVoted) 2.dp else 1.dp,
                                color = if (isVoted) InstagramPink else SmokeWhiteBorder,
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                selectedOptionId = opt.id
                                onVoteOption(opt.id)
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(opt.text, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = InstagramTextDark)
                                Text("${String.format("%.0f", percent)}%", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = InstagramPink)
                            }

                            LinearProgressIndicator(
                                progress = { (opt.votes.toFloat() / poll.totalVotes.toFloat()).coerceIn(0f, 1f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(CircleShape),
                                color = InstagramPink,
                                trackColor = SmokeWhiteBorder
                            )
                        }
                    }
                }
            }
        }
    }
}
