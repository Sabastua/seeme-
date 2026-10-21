package com.example.seeme.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.ui.theme.*

data class PayoutRequest(
    val id: String,
    val creatorName: String,
    val handle: String,
    val countryFlag: String,
    val requestedUsd: Double,
    val coinsAmount: Int,
    var isApproved: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val requests = remember {
        mutableStateListOf(
            PayoutRequest("p1", "Alex Riviera", "@alex_live", "🇺🇸", 1284.80, 1460),
            PayoutRequest("p2", "Amina Wanjiku", "@amina_tech", "🇰🇪", 940.00, 1068),
            PayoutRequest("p3", "Kenji Sato", "@kenji_beats", "🇯🇵", 2100.00, 2386)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Admin Revenue Portal", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InstagramTextDark) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = InstagramTextDark)
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
            // Platform Revenue Breakdown Card
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Filled.AdminPanelSettings, contentDescription = null, tint = InstagramPink, modifier = Modifier.size(26.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("System Financial Summary", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = InstagramTextDark)
                        }

                        Divider(color = SmokeWhiteBorder)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Gross Platform Volume", fontSize = 11.sp, color = InstagramTextSecondary)
                                Text("$124,500.00", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = InstagramTextDark)
                            }

                            Column {
                                Text("🔴 60% Admin Fee", fontSize = 11.sp, color = InstagramTextSecondary)
                                Text("$74,700.00", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = InstagramPink)
                            }

                            Column {
                                Text("🟢 40% Creator Pool", fontSize = 11.sp, color = InstagramTextSecondary)
                                Text("$49,800.00", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF2E7D32))
                            }
                        }
                    }
                }
            }

            // Pending Creator Payout Requests
            item {
                Text("Pending Payout Withdrawal Requests", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = InstagramTextDark)
            }

            items(requests, key = { it.id }) { req ->
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(18.dp))
                        .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(18.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("${req.countryFlag} ${req.creatorName}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = InstagramTextDark)
                            }
                            Text("${req.handle} • 🪙 ${req.coinsAmount} Coins", fontSize = 12.sp, color = InstagramTextSecondary)
                            Text("Payout Requested: $${String.format("%.2f", req.requestedUsd)}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF2E7D32))
                        }

                        if (req.isApproved) {
                            Box(
                                modifier = Modifier
                                    .background(Color(0xFF2E7D32).copy(alpha = 0.15f), CircleShape)
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Approved ✓", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        } else {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IconButton(
                                    onClick = {
                                        val idx = requests.indexOf(req)
                                        if (idx != -1) {
                                            requests[idx] = req.copy(isApproved = true)
                                        }
                                    },
                                    modifier = Modifier
                                        .background(Color(0xFF2E7D32), CircleShape)
                                        .size(36.dp)
                                ) {
                                    Icon(imageVector = Icons.Filled.Check, contentDescription = "Approve", tint = Color.White)
                                }

                                IconButton(
                                    onClick = { requests.remove(req) },
                                    modifier = Modifier
                                        .background(Color.Red, CircleShape)
                                        .size(36.dp)
                                ) {
                                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Reject", tint = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
