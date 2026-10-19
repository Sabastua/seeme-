package com.example.seeme.ui.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.data.model.CoinTransaction
import com.example.seeme.data.model.CoinWallet
import com.example.seeme.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    wallet: CoinWallet,
    transactions: List<CoinTransaction>,
    onBack: () -> Unit,
    onTopUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Coin Wallet & Revenue",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = InstagramTextDark
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = InstagramTextDark)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SmokeWhiteSurface)
            )
        },
        containerColor = SmokeWhiteBg,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Balance Card
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(6.dp, RoundedCornerShape(24.dp))
                        .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(24.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Total Creator Earnings", fontSize = 13.sp, color = InstagramTextSecondary, fontWeight = FontWeight.Medium)
                            Box(
                                modifier = Modifier
                                    .background(CoinGoldBg, CircleShape)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("🪙 ${wallet.coinsReceived} Coins", color = CoinGoldDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }

                        Text(
                            text = "$${String.format("%.2f", wallet.netCreatorPayoutUsd)}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = InstagramTextDark
                        )

                        Text(
                            text = "Net Payout Available (40% Creator Share of Gross $${String.format("%.2f", wallet.grossRevenueUsd)})",
                            fontSize = 12.sp,
                            color = InstagramTextSecondary
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = onTopUp,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = CoinGold),
                                shape = CircleShape
                            ) {
                                Icon(imageVector = Icons.Filled.MonetizationOn, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Top Up", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }

                            Button(
                                onClick = {},
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                                shape = CircleShape
                            ) {
                                Icon(imageVector = Icons.Filled.Payments, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Withdraw", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

            // Platform Revenue Split Card (60/40 Breakdown)
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SmokeWhiteCard),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(20.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Revenue Payout Policy (1 Coin = $2.20)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = InstagramTextDark)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Gross Tipping Value", fontSize = 13.sp, color = InstagramTextSecondary)
                            Text("$${String.format("%.2f", wallet.grossRevenueUsd)}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = InstagramTextDark)
                        }

                        LinearProgressIndicator(
                            progress = { 0.4f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = Color(0xFF2E7D32),
                            trackColor = InstagramPink.copy(alpha = 0.3f)
                        )

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("🔴 App/Admin Fee (60%): -$${String.format("%.2f", wallet.adminPlatformFeeUsd)}", fontSize = 12.sp, color = InstagramPink, fontWeight = FontWeight.SemiBold)
                            Text("🟢 Creator Share (40%): $${String.format("%.2f", wallet.netCreatorPayoutUsd)}", fontSize = 12.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Recent Transactions Title
            item {
                Text(
                    text = "Recent Coin Tip Transactions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = InstagramTextDark,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Transactions List
            if (transactions.isEmpty()) {
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth().border(1.dp, SmokeWhiteBorder, RoundedCornerShape(16.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No recent transactions yet.", color = InstagramTextSecondary, fontSize = 13.sp)
                        }
                    }
                }
            } else {
                items(transactions, key = { it.id }) { tx ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(16.dp))
                            .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(16.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(CoinGoldBg, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("🪙", fontSize = 18.sp)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(tx.creatorName, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = InstagramTextDark)
                                    Text("Tipped ${tx.coinAmount} Coins", fontSize = 12.sp, color = InstagramTextSecondary)
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text("+$${String.format("%.2f", tx.creatorPayoutUsd)}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF2E7D32))
                                Text("Gross: $${String.format("%.2f", tx.grossUsd)}", fontSize = 11.sp, color = InstagramTextSecondary)
                            }
                        }
                    }
                }
            }
        }
    }
}
