package com.example.seeme.ui.analytics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsStudioScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Broadcaster Analytics Studio", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InstagramTextDark) },
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
            // Metrics Summary Grid
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Stream Performance Overview", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = InstagramTextDark)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AnalyticsMetricCard(
                            title = "Peak Viewers (CCU)",
                            value = "2,840",
                            subtitle = "+24% vs last stream",
                            icon = Icons.Filled.Group,
                            accentColor = InstagramPink,
                            modifier = Modifier.weight(1f)
                        )

                        AnalyticsMetricCard(
                            title = "Total Watch Hours",
                            value = "142.5 hrs",
                            subtitle = "Average 32 mins/user",
                            icon = Icons.Filled.Schedule,
                            accentColor = Color(0xFF007AFF),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AnalyticsMetricCard(
                            title = "Coin Rate / Min",
                            value = "🪙 18.5/min",
                            subtitle = "1,450 coins total",
                            icon = Icons.Filled.MonetizationOn,
                            accentColor = CoinGold,
                            modifier = Modifier.weight(1f)
                        )

                        AnalyticsMetricCard(
                            title = "Hooked Retention",
                            value = "98%",
                            subtitle = "Top 1% engagement",
                            icon = Icons.Filled.BarChart,
                            accentColor = Color(0xFF2E7D32),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Audience Retention Graph Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(20.dp))
                        .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(20.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Viewer Retention Graph", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = InstagramTextDark)

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            RetentionBarItem("0m - 10m (Hook Phase)", 0.98f, "98% Stayed")
                            RetentionBarItem("10m - 30m (Peak Stream)", 0.94f, "94% Stayed")
                            RetentionBarItem("30m - 60m (Q&A & Gifts)", 0.88f, "88% Stayed")
                        }
                    }
                }
            }

            // AI Auto-Clipped Reels Section
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Filled.ContentCut, contentDescription = null, tint = InstagramPink, modifier = Modifier.size(22.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("AI Auto-Generated Reel Highlights", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = InstagramTextDark)
                        }

                        Text("AI detected 3 viral moments during your stream and clipped them for Instagram Reels & Shorts.", fontSize = 12.sp, color = InstagramTextSecondary)

                        Button(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                            shape = CircleShape
                        ) {
                            Text("Publish 3 Clips to Profile Reels", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnalyticsMetricCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
        modifier = modifier
            .shadow(3.dp, RoundedCornerShape(18.dp))
            .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(18.dp))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(24.dp))
            Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = InstagramTextDark)
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, color = InstagramTextDark)
            Text(subtitle, fontSize = 10.sp, color = InstagramTextSecondary)
        }
    }
}

@Composable
fun RetentionBarItem(label: String, progress: Float, percentText: String) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, fontSize = 11.sp, color = InstagramTextSecondary)
            Text(percentText, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = InstagramPink)
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = InstagramPink,
            trackColor = SmokeWhiteBorder
        )
    }
}
