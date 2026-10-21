package com.example.seeme.ui.pk

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seeme.ui.coin.CoinViewModel
import com.example.seeme.ui.components.GiftGalleryDialog
import com.example.seeme.ui.components.PulsingDot
import com.example.seeme.ui.components.VideoPlayer
import com.example.seeme.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PKBattleScreen(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    pkViewModel: PKViewModel = viewModel(),
    coinViewModel: CoinViewModel = viewModel()
) {
    val battle by pkViewModel.activeBattle.collectAsState()
    val goal by pkViewModel.streamerGoal.collectAsState()
    val wallet by coinViewModel.wallet.collectAsState()
    val activeBanner by pkViewModel.lastSentGiftBanner.collectAsState()

    var showGiftSheetForCreatorId by remember { mutableStateOf<String?>(null) }

    val activePK = battle ?: return

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // SPLIT SCREEN VIDEO FEEDS
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Half: Creator 1
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFF007AFF))
            ) {
                VideoPlayer(
                    videoUrl = activePK.creator1.videoUrl,
                    modifier = Modifier.fillMaxSize()
                )

                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${activePK.creator1.country.flagEmoji} ${activePK.creator1.name}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            // Bottom Half: Creator 2
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .border(2.dp, Color(0xFFFF2D55))
            ) {
                VideoPlayer(
                    videoUrl = activePK.creator2.videoUrl,
                    modifier = Modifier.fillMaxSize()
                )

                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${activePK.creator2.country.flagEmoji} ${activePK.creator2.name}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        // OVERLAY CONTROLS & PK SCORE BAR
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Header & Close
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.Red, CircleShape)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    PulsingDot()
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("LIVE PK BATTLE ⚔️", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
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

            // PK BATTLE PROGRESS SCORE BAR
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🟦 ${activePK.creator1.name}: ${activePK.creator1Points} pts", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)

                    Box(
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        val mins = activePK.timeRemainingSeconds / 60
                        val secs = activePK.timeRemainingSeconds % 60
                        Text("⏱ ${String.format("%02d:%02d", mins, secs)}", color = CoinGold, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                    }

                    Text("🟥 ${activePK.creator2.name}: ${activePK.creator2Points} pts", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                // Split Ratio Progress Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp)
                        .clip(CircleShape)
                        .background(Color.DarkGray)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(activePK.creator1Ratio.coerceAtLeast(0.05f))
                            .fillMaxHeight()
                            .background(Color(0xFF007AFF))
                    )
                    Box(
                        modifier = Modifier
                            .weight(activePK.creator2Ratio.coerceAtLeast(0.05f))
                            .fillMaxHeight()
                            .background(Color(0xFFFF2D55))
                    )
                }
            }

            // BROADCASTER LIVE GOAL BAR & GIFT ACTION ROW
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Active Gift Sent Banner Toast
                AnimatedVisibility(visible = activeBanner != null) {
                    activeBanner?.let { banner ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CoinGoldBg),
                            shape = CircleShape,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("🎁 $banner", color = CoinGoldDark, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                            }
                        }
                    }
                }

                // Streamer Goal Progress Bar
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.65f)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(goal.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Text("${goal.currentCoins}/${goal.targetCoins} Coins", color = CoinGold, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        LinearProgressIndicator(
                            progress = { goal.progressPercent },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(CircleShape),
                            color = CoinGold,
                            trackColor = Color.White.copy(alpha = 0.2f)
                        )
                    }
                }

                // Action Row: Send Gifts to Creator 1 or Creator 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { showGiftSheetForCreatorId = activePK.creator1.id },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
                        shape = CircleShape
                    ) {
                        Text("🎁 Gift ${activePK.creator1.name.take(6)}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                    }

                    Button(
                        onClick = { showGiftSheetForCreatorId = activePK.creator2.id },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D55)),
                        shape = CircleShape
                    ) {
                        Text("🎁 Gift ${activePK.creator2.name.take(6)}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                    }
                }
            }
        }

        // Virtual Gift Gallery Modal Sheet
        showGiftSheetForCreatorId?.let { creatorId ->
            GiftGalleryDialog(
                virtualGifts = pkViewModel.virtualGifts,
                userCoins = wallet.coinsAvailable,
                onDismiss = { showGiftSheetForCreatorId = null },
                onSendGift = { gift ->
                    pkViewModel.sendVirtualGift(creatorId, gift)
                    showGiftSheetForCreatorId = null
                },
                onTopUp = {
                    showGiftSheetForCreatorId = null
                }
            )
        }
    }
}
