package com.example.seeme.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Watch
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WearCompanionDialog(
    onDismiss: () -> Unit
) {
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
                    Icon(imageVector = Icons.Filled.Watch, contentDescription = null, tint = InstagramPink, modifier = Modifier.size(26.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Wear OS Watch Companion", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InstagramTextDark)
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = InstagramTextSecondary)
                }
            }

            // Watch Face Simulation
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .align(Alignment.CenterHorizontally)
                    .shadow(8.dp, CircleShape)
                    .background(InstagramTextDark, CircleShape)
                    .border(3.dp, InstagramPink, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("LIVE CCU", fontSize = 10.sp, color = InstagramPink, fontWeight = FontWeight.ExtraBold)
                    Text("2,840", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    Text("18.5 Coins/min", fontSize = 11.sp, color = CoinGold, fontWeight = FontWeight.Bold)
                    Text("Wrist Haptics: ON", fontSize = 9.sp, color = Color.Gray)
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = SmokeWhiteCard),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("Wrist Notification Status", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = InstagramTextDark)
                    Text("Connected to Wear OS Smartwatch. Receiving wrist vibration alerts for coin gifts and viewer milestones.", fontSize = 11.sp, color = InstagramTextSecondary)
                }
            }
        }
    }
}
