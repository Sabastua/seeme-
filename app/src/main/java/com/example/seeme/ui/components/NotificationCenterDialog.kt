package com.example.seeme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.ui.theme.*

data class AppNotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val time: String,
    val iconEmoji: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationCenterDialog(
    onDismiss: () -> Unit
) {
    val alerts = listOf(
        AppNotificationItem("n1", "Alex Riviera is LIVE!", "Alex Riviera started streaming: Android & Compose live coding 🚀", "2m ago", "🔴"),
        AppNotificationItem("n2", "Coin Gift Received", "DevDan tipped you 🪙 50 Coins ($110.00)", "15m ago", "🪙"),
        AppNotificationItem("n3", "New VIP Fan Club Member", "Sarah_K subscribed to your VIP Fan Club ($4.99/mo)", "1h ago", "🌟"),
        AppNotificationItem("n4", "Direct Message", "Kenji Sato sent you a private message", "3h ago", "💬")
    )

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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.Notifications, contentDescription = null, tint = InstagramPink, modifier = Modifier.size(26.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Notifications & Alerts", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InstagramTextDark)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = InstagramTextSecondary)
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                items(alerts, key = { it.id }) { alert ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SmokeWhiteCard),
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(2.dp, RoundedCornerShape(16.dp))
                            .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(16.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(SmokeWhiteSurface, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(alert.iconEmoji, fontSize = 20.sp)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(alert.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = InstagramTextDark)
                                Text(alert.message, fontSize = 11.sp, color = InstagramTextSecondary, maxLines = 2)
                            }

                            Text(alert.time, fontSize = 10.sp, color = InstagramTextSecondary)
                        }
                    }
                }
            }
        }
    }
}
