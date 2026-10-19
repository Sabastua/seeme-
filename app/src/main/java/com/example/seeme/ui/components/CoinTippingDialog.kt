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
import com.example.seeme.data.model.CoinPackage
import com.example.seeme.data.model.VideoItem
import com.example.seeme.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinTippingDialog(
    videoItem: VideoItem,
    availableCoins: Int,
    packages: List<CoinPackage>,
    onDismiss: () -> Unit,
    onSendTip: (CoinPackage) -> Unit
) {
    var selectedPackage by remember { mutableStateOf(packages.firstOrNull() ?: CoinPackage(1, "1 Coin")) }
    val haptic = LocalHapticFeedback.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SmokeWhiteSurface,
        scrimColor = Color.Black.copy(alpha = 0.4f),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.MonetizationOn,
                        contentDescription = "Coins",
                        tint = CoinGold,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Tip ${videoItem.creatorName}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = InstagramTextDark
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = InstagramTextSecondary)
                }
            }

            // User Coin Balance Card
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
                    Text(text = "Your Coin Balance", color = CoinGoldDark, fontWeight = FontWeight.Medium, fontSize = 13.sp)
                    Text(text = "🪙 $availableCoins Coins", color = CoinGoldDark, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }

            // Coin Package Selection Row
            Text(text = "Select Tip Amount", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = InstagramTextDark)

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(packages) { pkg ->
                    val isSelected = selectedPackage == pkg
                    Box(
                        modifier = Modifier
                            .shadow(if (isSelected) 4.dp else 1.dp, RoundedCornerShape(16.dp))
                            .background(if (isSelected) InstagramPink else SmokeWhiteSurface, RoundedCornerShape(16.dp))
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) InstagramPink else SmokeWhiteBorder,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedPackage = pkg
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "🪙 ${pkg.coinAmount}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = if (isSelected) Color.White else InstagramTextDark
                            )
                            Text(
                                text = "$${String.format("%.2f", pkg.grossUsd)}",
                                fontSize = 11.sp,
                                color = if (isSelected) Color.White.copy(alpha = 0.9f) else InstagramTextSecondary
                            )
                        }
                    }
                }
            }

            // Financial Revenue Split Card (Transparent 60/40 Breakdown)
            Card(
                colors = CardDefaults.cardColors(containerColor = SmokeWhiteCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = "Transaction Breakdown", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = InstagramTextDark)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Gross Tip Value (1 Coin = $2.20):", fontSize = 12.sp, color = InstagramTextSecondary)
                        Text(text = "$${String.format("%.2f", selectedPackage.grossUsd)}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = InstagramTextDark)
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "App / Admin Fee (60%):", fontSize = 12.sp, color = InstagramTextSecondary)
                        Text(text = "$${String.format("%.2f", selectedPackage.adminFeeUsd)}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = InstagramPink)
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "Creator Net Payout (40%):", fontSize = 12.sp, color = InstagramTextSecondary)
                        Text(text = "$${String.format("%.2f", selectedPackage.creatorPayoutUsd)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    }
                }
            }

            // Send Tip Action Button with Haptic Vibration Feedback
            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onSendTip(selectedPackage)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .shadow(4.dp, CircleShape),
                colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                shape = CircleShape
            ) {
                Text(
                    text = "Send 🪙 ${selectedPackage.coinAmount} Coin Tip",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
