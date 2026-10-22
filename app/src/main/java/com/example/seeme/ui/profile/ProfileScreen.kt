package com.example.seeme.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.GridOn
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.data.model.CoinWallet
import com.example.seeme.data.model.UserProfile
import com.example.seeme.ui.components.GlassButton
import com.example.seeme.ui.components.GlassCard
import com.example.seeme.ui.theme.*

data class PostThumbnail(
    val id: String,
    val title: String,
    val views: String,
    val likes: String,
    val isLiveReplay: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userProfile: UserProfile,
    wallet: CoinWallet,
    onRequireAuth: () -> Unit = {},
    onEditProfile: () -> Unit = {},
    onOpenWallet: () -> Unit = {},
    onTopUpCoins: () -> Unit = {},
    onOpenAnalytics: () -> Unit = {},
    onOpenAdmin: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val samplePosts = listOf(
        PostThumbnail("1", "Live Coding #1", "12.4K", "1.2K", true),
        PostThumbnail("2", "Compose Tips", "8.9K", "950"),
        PostThumbnail("3", "Q&A Stream", "15.1K", "2.1K", true),
        PostThumbnail("4", "Studio Tour", "22.0K", "3.4K"),
        PostThumbnail("5", "Setup 2026", "18.5K", "2.8K"),
        PostThumbnail("6", "UI Design Live", "9.2K", "1.1K", true)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SmokeWhiteBg)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Header Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (userProfile.isGuest) "guest_viewer" else userProfile.handle.removePrefix("@"),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = InstagramTextDark
                )
                if (!userProfile.isGuest) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Filled.Verified,
                        contentDescription = "Verified",
                        tint = EmeraldGreenPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = onOpenAnalytics) {
                    Icon(imageVector = Icons.Filled.BarChart, contentDescription = "Analytics", tint = InstagramTextDark)
                }
                IconButton(onClick = onOpenAdmin) {
                    Icon(imageVector = Icons.Filled.AdminPanelSettings, contentDescription = "Admin", tint = EmeraldGreenPrimary)
                }
            }
        }

        // Guest Mode Glass Banner
        if (userProfile.isGuest) {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                cornerRadius = 20.dp,
                borderColor = EmeraldGreenPrimary.copy(alpha = 0.4f)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("👤", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Guest Mode Active", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = InstagramTextDark)
                            Text("Watch streams & tip coins. Sign up required to Go Live!", fontSize = 12.sp, color = InstagramTextSecondary)
                        }
                    }

                    GlassButton(
                        onClick = onRequireAuth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        containerColor = EmeraldGreenPrimary
                    ) {
                        Text("Sign Up as Creator to Go Live 🚀", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                    }
                }
            }
        }

        // Profile Header Details
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(InstagramGradientStart, EmeraldGreenPrimary, InstagramGradientEnd)
                        ),
                        shape = CircleShape
                    )
                    .padding(3.dp)
                    .background(SmokeWhiteBg, CircleShape)
                    .padding(3.dp)
                    .background(EmeraldGreenPrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userProfile.name.take(2).uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStatItem("Posts", if (userProfile.isGuest) "0" else "18")
                ProfileStatItem("Followers", if (userProfile.isGuest) "0" else "12.4K")
                ProfileStatItem("Following", if (userProfile.isGuest) "0" else "482")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = "${userProfile.countryFlag} ${userProfile.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = InstagramTextDark
            )
            Text(
                text = "${userProfile.category} • SeeMe Creator",
                fontSize = 13.sp,
                color = InstagramTextSecondary
            )
            Text(
                text = userProfile.bio,
                fontSize = 13.sp,
                color = InstagramTextDark
            )
        }

        // Action Buttons Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { if (userProfile.isGuest) onRequireAuth() else onEditProfile() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, SmokeWhiteBorder),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = InstagramTextDark)
            ) {
                Text(if (userProfile.isGuest) "Sign Up" else "Edit Profile", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }

            OutlinedButton(
                onClick = {},
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, SmokeWhiteBorder),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = InstagramTextDark)
            ) {
                Text("Share Profile", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }

            Button(
                onClick = onTopUpCoins,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CoinGold)
            ) {
                Icon(imageVector = Icons.Filled.MonetizationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Top Up", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Liquid Glass Creator Coin Wallet Card
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            cornerRadius = 20.dp,
            elevation = 6.dp,
            onClick = onOpenWallet
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.MonetizationOn, contentDescription = null, tint = CoinGold, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Creator Coin Wallet", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = InstagramTextDark)
                    }

                    Box(
                        modifier = Modifier
                            .background(CoinGoldBg, CircleShape)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(text = "🪙 ${wallet.coinsReceived} Received", color = CoinGoldDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }

                HorizontalDivider(color = SmokeWhiteBorder)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Gross Tipping Revenue", fontSize = 11.sp, color = InstagramTextSecondary)
                        Text(text = "$${String.format("%.2f", wallet.grossRevenueUsd)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = InstagramTextDark)
                    }

                    Column {
                        Text(text = "App Fee (60%)", fontSize = 11.sp, color = InstagramTextSecondary)
                        Text(text = "-$${String.format("%.2f", wallet.adminPlatformFeeUsd)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = EmeraldGreenPrimary)
                    }

                    Column {
                        Text(text = "Net Payout (40%)", fontSize = 11.sp, color = InstagramTextSecondary)
                        Text(text = "$${String.format("%.2f", wallet.netCreatorPayoutUsd)}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    }
                }

                GlassButton(
                    onClick = onOpenWallet,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    containerColor = InstagramTextDark
                ) {
                    Text(text = "View Wallet Details & Withdraw", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Instagram Grid Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(GlassSurfaceWhite)
        ) {
            TabItem(
                icon = Icons.Outlined.GridOn,
                isSelected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                modifier = Modifier.weight(1f)
            )
            TabItem(
                icon = Icons.Outlined.VideoLibrary,
                isSelected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                modifier = Modifier.weight(1f)
            )
            TabItem(
                icon = Icons.Outlined.BookmarkBorder,
                isSelected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                modifier = Modifier.weight(1f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            samplePosts.chunked(2).forEach { rowPosts ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowPosts.forEach { post ->
                        GlassCard(
                            modifier = Modifier
                                .weight(1f)
                                .height(160.dp),
                            cornerRadius = 14.dp,
                            elevation = 2.dp
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    if (post.isLiveReplay) {
                                        Box(
                                            modifier = Modifier
                                                .background(EmeraldGreenPrimary, CircleShape)
                                                .padding(horizontal = 8.dp, vertical = 2.dp)
                                        ) {
                                            Text("REPLAY", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    Column {
                                        Text(text = post.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = InstagramTextDark)
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(text = "👁 ${post.views}", fontSize = 11.sp, color = InstagramTextSecondary)
                                            Text(text = "❤️ ${post.likes}", fontSize = 11.sp, color = InstagramTextSecondary)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileStatItem(label: String, count: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = InstagramTextDark)
        Text(text = label, fontSize = 12.sp, color = InstagramTextSecondary)
    }
}

@Composable
fun TabItem(
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) EmeraldGreenPrimary else InstagramTextSecondary,
            modifier = Modifier.size(24.dp)
        )
    }
}
