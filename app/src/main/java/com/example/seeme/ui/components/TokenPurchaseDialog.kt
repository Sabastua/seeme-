package com.example.seeme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.seeme.data.model.CurrencyOption
import com.example.seeme.data.model.PaymentGateway
import com.example.seeme.data.model.TokenPackage
import com.example.seeme.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TokenPurchaseDialog(
    currencies: List<CurrencyOption>,
    paymentGateways: List<PaymentGateway>,
    tokenPackages: List<TokenPackage>,
    onDismiss: () -> Unit,
    onConfirmPurchase: (TokenPackage, PaymentGateway, CurrencyOption) -> Unit
) {
    var selectedCurrency by remember { mutableStateOf(currencies.first()) }
    var selectedPackage by remember { mutableStateOf(tokenPackages[1]) } // Default 50 Tokens
    var selectedGateway by remember { mutableStateOf(paymentGateways.first()) }
    val haptic = LocalHapticFeedback.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SmokeWhiteSurface,
        scrimColor = Color.Black.copy(alpha = 0.45f),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.MonetizationOn, contentDescription = null, tint = CoinGold, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Buy Tokens / Coins", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = InstagramTextDark)
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = InstagramTextSecondary)
                    }
                }
            }

            // Currency Switcher Row
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Select Currency & Region", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = InstagramTextDark)

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(currencies) { curr ->
                            val isSelected = selectedCurrency == curr
                            Box(
                                modifier = Modifier
                                    .shadow(if (isSelected) 3.dp else 1.dp, CircleShape)
                                    .background(if (isSelected) InstagramPink else SmokeWhiteSurface, CircleShape)
                                    .border(1.dp, if (isSelected) InstagramPink else SmokeWhiteBorder, CircleShape)
                                    .clickable {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        selectedCurrency = curr
                                    }
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(curr.flagEmoji, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = curr.code,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (isSelected) Color.White else InstagramTextDark
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Token Bundle Selection Row
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Choose Token Bundle", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = InstagramTextDark)

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(tokenPackages) { pkg ->
                            val isSelected = selectedPackage == pkg
                            Box(
                                modifier = Modifier
                                    .shadow(if (isSelected) 4.dp else 1.dp, RoundedCornerShape(16.dp))
                                    .background(if (isSelected) CoinGold else SmokeWhiteSurface, RoundedCornerShape(16.dp))
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) CoinGold else SmokeWhiteBorder,
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
                                        text = "🪙 ${pkg.tokenAmount}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = if (isSelected) Color.White else InstagramTextDark
                                    )
                                    Text(
                                        text = pkg.formatLocalPrice(selectedCurrency),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isSelected) Color.White.copy(alpha = 0.9f) else InstagramTextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Payment Gateways Section
            item {
                Text("Select Payment Method", fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = InstagramTextDark)
            }

            items(paymentGateways, key = { it.id }) { gateway ->
                val isSelected = selectedGateway == gateway
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isSelected) SmokeWhiteCard else SmokeWhiteSurface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(if (isSelected) 3.dp else 1.dp, RoundedCornerShape(16.dp))
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) InstagramPink else SmokeWhiteBorder,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            selectedGateway = gateway
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
                            Text(gateway.iconEmoji, fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(gateway.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = InstagramTextDark)
                                Text(gateway.subtitle, fontSize = 11.sp, color = InstagramTextSecondary)
                            }
                        }

                        RadioButton(
                            selected = isSelected,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedGateway = gateway
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = InstagramPink)
                        )
                    }
                }
            }

            // Confirm Purchase Action
            item {
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onConfirmPurchase(selectedPackage, selectedGateway, selectedCurrency)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .shadow(6.dp, CircleShape),
                    colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                    shape = CircleShape
                ) {
                    Text(
                        text = "Pay ${selectedPackage.formatLocalPrice(selectedCurrency)} & Claim 🪙 ${selectedPackage.tokenAmount} Tokens",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
