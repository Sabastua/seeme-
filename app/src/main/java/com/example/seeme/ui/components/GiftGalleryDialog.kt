package com.example.seeme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MonetizationOn
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
import com.example.seeme.data.model.VirtualGift
import com.example.seeme.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GiftGalleryDialog(
    virtualGifts: List<VirtualGift>,
    userCoins: Int,
    onDismiss: () -> Unit,
    onSendGift: (VirtualGift) -> Unit,
    onTopUp: () -> Unit
) {
    var selectedGift by remember { mutableStateOf(virtualGifts.first()) }
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
                    Text("🎁", fontSize = 26.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Send 3D Virtual Gift", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InstagramTextDark)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = InstagramTextSecondary)
                }
            }

            // Coin Balance Bar
            Card(
                colors = CardDefaults.cardColors(containerColor = CoinGoldBg),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.MonetizationOn, contentDescription = null, tint = CoinGold, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("🪙 $userCoins Coins Available", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = CoinGoldDark)
                    }

                    TextButton(onClick = onTopUp) {
                        Text("+ Top Up", fontWeight = FontWeight.ExtraBold, color = InstagramPink, fontSize = 13.sp)
                    }
                }
            }

            // Gift Gallery Grid
            Text("Virtual Gift Gallery", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = InstagramTextDark)

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
            ) {
                items(virtualGifts, key = { it.id }) { gift ->
                    val isSelected = selectedGift == gift
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = if (isSelected) CoinGoldBg else SmokeWhiteCard),
                        modifier = Modifier
                            .shadow(if (isSelected) 4.dp else 1.dp, RoundedCornerShape(18.dp))
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) CoinGold else SmokeWhiteBorder,
                                shape = RoundedCornerShape(18.dp)
                            )
                            .clickable {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedGift = gift
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(gift.iconEmoji, fontSize = 34.sp)
                            Text(gift.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = InstagramTextDark, maxLines = 1)
                            Text("🪙 ${gift.coinCost} ($${String.format("%.2f", gift.coinCost * 2.20)})", fontSize = 10.sp, color = InstagramTextSecondary, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            // Send Gift Action Button
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onSendGift(selectedGift)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(6.dp, CircleShape),
                colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                shape = CircleShape
            ) {
                Text(
                    text = "Send ${selectedGift.iconEmoji} ${selectedGift.name} (🪙 ${selectedGift.coinCost} Coins)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
        }
    }
}
