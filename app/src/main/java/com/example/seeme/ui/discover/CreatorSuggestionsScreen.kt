package com.example.seeme.ui.discover

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seeme.data.model.CreatorProfile
import com.example.seeme.ui.components.PulsingDot
import com.example.seeme.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatorSuggestionsScreen(
    onBack: () -> Unit,
    onSelectCreator: (CreatorProfile) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = viewModel()
) {
    val topRatedCreator by viewModel.topRatedCreator.collectAsState()
    val newCreators by viewModel.newCreators.collectAsState()
    val allCreators by viewModel.filteredCreators.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Suggested Creators", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = InstagramTextDark) },
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
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // #1 TOP RATED STREAM PODIUM BANNER
            topRatedCreator?.let { topCreator ->
                item {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .shadow(10.dp, RoundedCornerShape(24.dp))
                            .border(2.dp, CoinGold, RoundedCornerShape(24.dp))
                            .clickable { onSelectCreator(topCreator) }
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(CoinGoldBg, CircleShape)
                                        .border(1.dp, CoinGold, CircleShape)
                                        .padding(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text("👑 #1 TOP RATED STREAM", color = CoinGoldDark, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                                }

                                Text("${topCreator.country.flagEmoji} ${topCreator.country.name}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = InstagramTextDark)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(
                                            brush = Brush.linearGradient(listOf(CoinGold, InstagramPink)),
                                            shape = CircleShape
                                        )
                                        .padding(3.dp)
                                        .background(SmokeWhiteSurface, CircleShape)
                                        .padding(2.dp)
                                        .background(CoinGold, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(topCreator.name.take(1).uppercase(), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 22.sp)
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column {
                                    Text(topCreator.name, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = InstagramTextDark)
                                    Text("${topCreator.handle} • ${topCreator.category}", fontSize = 13.sp, color = InstagramTextSecondary)
                                }
                            }

                            // Stream Quality Rating & Hooked Audience Card
                            Card(
                                colors = CardDefaults.cardColors(containerColor = SmokeWhiteCard),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(imageVector = Icons.Filled.Star, contentDescription = null, tint = CoinGold, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("${topCreator.streamQualityScore}/5.0", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = InstagramTextDark)
                                        }
                                        Text("Stream Quality", fontSize = 11.sp, color = InstagramTextSecondary)
                                    }

                                    VerticalDivider(modifier = Modifier.height(30.dp), color = SmokeWhiteBorder)

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("🔥 ${topCreator.hookedAudiencePercent}%", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = InstagramPink)
                                        Text("Hooked Audience", fontSize = 11.sp, color = InstagramTextSecondary)
                                    }

                                    VerticalDivider(modifier = Modifier.height(30.dp), color = SmokeWhiteBorder)

                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            PulsingDot()
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("${topCreator.viewerCount}", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Red)
                                        }
                                        Text("Live Viewers", fontSize = 11.sp, color = InstagramTextSecondary)
                                    }
                                }
                            }

                            Button(
                                onClick = { onSelectCreator(topCreator) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                                shape = CircleShape
                            ) {
                                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Watch #1 Top Rated Stream", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                            }
                        }
                    }
                }
            }

            // NEW CREATORS CAROUSEL SECTION
            if (newCreators.isNotEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("✨ Newly Joined Streamers", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = InstagramTextDark)
                            Text("${newCreators.size} New", fontSize = 12.sp, color = InstagramPink, fontWeight = FontWeight.Bold)
                        }

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(newCreators, key = { it.id }) { creator ->
                                Card(
                                    shape = RoundedCornerShape(18.dp),
                                    colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
                                    modifier = Modifier
                                        .width(180.dp)
                                        .shadow(3.dp, RoundedCornerShape(18.dp))
                                        .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(18.dp))
                                        .clickable { onSelectCreator(creator) }
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .background(InstagramPink.copy(alpha = 0.12f), CircleShape)
                                                .padding(horizontal = 10.dp, vertical = 4.dp)
                                        ) {
                                            Text("✨ NEW", color = InstagramPink, fontWeight = FontWeight.ExtraBold, fontSize = 10.sp)
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(52.dp)
                                                .background(
                                                    brush = Brush.linearGradient(listOf(InstagramGradientStart, InstagramPink)),
                                                    shape = CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(creator.name.take(1).uppercase(), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                                        }

                                        Text(creator.name, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = InstagramTextDark, maxLines = 1)
                                        Text("${creator.country.flagEmoji} ${creator.category}", fontSize = 11.sp, color = InstagramTextSecondary)

                                        Button(
                                            onClick = { onSelectCreator(creator) },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(34.dp),
                                            colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                                            shape = CircleShape,
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Text("Watch", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ALL SUGGESTED CREATORS LIST
            item {
                Text("All Suggested Creators", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = InstagramTextDark)
            }

            items(allCreators, key = { it.id }) { creator ->
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(3.dp, RoundedCornerShape(20.dp))
                        .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(20.dp))
                        .clickable { onSelectCreator(creator) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .background(
                                        brush = Brush.linearGradient(listOf(InstagramPink, InstagramGradientEnd)),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(creator.name.take(1).uppercase(), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(creator.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = InstagramTextDark)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("${creator.country.flagEmoji}", fontSize = 13.sp)

                                    if (creator.isNewCreator) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .background(InstagramPink.copy(alpha = 0.15f), CircleShape)
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text("✨ NEW", color = InstagramPink, fontWeight = FontWeight.ExtraBold, fontSize = 9.sp)
                                        }
                                    }
                                }

                                Text("${creator.handle} • ${creator.category}", fontSize = 12.sp, color = InstagramTextSecondary)

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.padding(top = 2.dp)
                                ) {
                                    Text("⭐ ${creator.streamQualityScore}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CoinGoldDark)
                                    Text("🔥 ${creator.hookedAudiencePercent}% Hooked", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = InstagramPink)
                                }
                            }
                        }

                        Button(
                            onClick = { onSelectCreator(creator) },
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text("Watch", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
