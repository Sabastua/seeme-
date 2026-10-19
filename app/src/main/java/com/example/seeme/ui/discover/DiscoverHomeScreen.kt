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
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seeme.data.model.CreatorCountry
import com.example.seeme.data.model.CreatorProfile
import com.example.seeme.ui.components.PulsingDot
import com.example.seeme.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverHomeScreen(
    onSelectCreatorStream: (CreatorProfile) -> Unit,
    onOpenSuggestions: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = viewModel()
) {
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val selectedCountry by viewModel.selectedCountry.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val creators by viewModel.filteredCreators.collectAsState()

    val featuredCreator = creators.firstOrNull { it.isLive } ?: creators.firstOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SmokeWhiteBg)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header Row
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SeeMe",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 26.sp,
                        color = InstagramTextDark
                    )

                    Box(
                        modifier = Modifier
                            .background(CoinGoldBg, CircleShape)
                            .border(1.dp, CoinGold.copy(alpha = 0.4f), CircleShape)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Filled.MonetizationOn, contentDescription = null, tint = CoinGold, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "250 Coins", color = CoinGoldDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    placeholder = { Text("Search creators, categories or countries...", color = InstagramTextSecondary, fontSize = 13.sp) },
                    leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null, tint = InstagramTextSecondary) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = CircleShape,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = SmokeWhiteSurface,
                        unfocusedContainerColor = SmokeWhiteSurface,
                        focusedTextColor = InstagramTextDark,
                        unfocusedTextColor = InstagramTextDark,
                        focusedIndicatorColor = InstagramPink,
                        unfocusedIndicatorColor = SmokeWhiteBorder
                    ),
                    singleLine = true
                )
            }
        }

        // Hero Featured Stream Banner
        featuredCreator?.let { hero ->
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(24.dp))
                        .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(24.dp))
                        .clickable { onSelectCreatorStream(hero) }
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
                            Box(
                                modifier = Modifier
                                    .background(Color.Red, CircleShape)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    PulsingDot()
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("FEATURED LIVE", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .background(SmokeWhiteCard, CircleShape)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("${hero.country.flagEmoji} ${hero.country.name}", fontWeight = FontWeight.SemiBold, fontSize = 12.sp, color = InstagramTextDark)
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .background(
                                        brush = Brush.linearGradient(listOf(InstagramGradientStart, InstagramPink)),
                                        shape = CircleShape
                                    )
                                    .padding(2.dp)
                                    .background(SmokeWhiteSurface, CircleShape)
                                    .padding(2.dp)
                                    .background(InstagramPink, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(hero.name.take(1).uppercase(), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 20.sp)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(hero.name, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = InstagramTextDark)
                                Text("${hero.handle} • 💻 ${hero.category}", fontSize = 12.sp, color = InstagramTextSecondary)
                            }
                        }

                        Text(hero.bio, fontSize = 13.sp, color = InstagramTextDark, maxLines = 2)

                        Button(
                            onClick = { onSelectCreatorStream(hero) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = InstagramPink),
                            shape = CircleShape
                        ) {
                            Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Watch Live Stream (${hero.viewerCount} Viewers)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.White)
                        }
                    }
                }
            }
        }

        // Suggestions & Rankings Banner
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CoinGoldBg),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CoinGold.copy(alpha = 0.5f), RoundedCornerShape(18.dp))
                    .clickable { onOpenSuggestions() }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("👑", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Creator Suggestions & Rankings", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = InstagramTextDark)
                            Text("✨ Discover new creators & #1 top rated streams", fontSize = 12.sp, color = InstagramTextSecondary)
                        }
                    }

                    Text("Explore ➔", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = InstagramPink)
                }
            }
        }

        // Categories Filter Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Explore Categories", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = InstagramTextDark)

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(viewModel.categories) { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .shadow(if (isSelected) 3.dp else 1.dp, CircleShape)
                                .background(if (isSelected) InstagramPink else SmokeWhiteSurface, CircleShape)
                                .border(1.dp, if (isSelected) InstagramPink else SmokeWhiteBorder, CircleShape)
                                .clickable { viewModel.selectCategory(cat) }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = cat,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = if (isSelected) Color.White else InstagramTextDark
                            )
                        }
                    }
                }
            }
        }

        // Countries Filter Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Browse by Country", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = InstagramTextDark)

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(viewModel.countries) { cty ->
                        val isSelected = selectedCountry.code == cty.code
                        Box(
                            modifier = Modifier
                                .shadow(if (isSelected) 3.dp else 1.dp, CircleShape)
                                .background(if (isSelected) InstagramTextDark else SmokeWhiteSurface, CircleShape)
                                .border(1.dp, if (isSelected) InstagramTextDark else SmokeWhiteBorder, CircleShape)
                                .clickable { viewModel.selectCountry(cty) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(cty.flagEmoji, fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = cty.name,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    color = if (isSelected) Color.White else InstagramTextDark
                                )
                            }
                        }
                    }
                }
            }
        }

        // Creator List Title
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Creators (${creators.size})", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = InstagramTextDark)
                Text("Real-time Live", fontSize = 12.sp, color = InstagramPink, fontWeight = FontWeight.SemiBold)
            }
        }

        // Creator Cards List
        items(creators, key = { it.id }) { creator ->
            CreatorCard(
                creator = creator,
                onWatchStream = { onSelectCreatorStream(creator) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CreatorCard(
    creator: CreatorProfile,
    onWatchStream: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SmokeWhiteSurface),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(3.dp, RoundedCornerShape(20.dp))
            .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(20.dp))
            .clickable { onWatchStream() }
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
                        )
                        .padding(2.dp)
                        .background(SmokeWhiteSurface, CircleShape)
                        .padding(2.dp)
                        .background(InstagramPink, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(creator.name.take(1).uppercase(), fontWeight = FontWeight.Bold, color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(creator.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = InstagramTextDark)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("${creator.country.flagEmoji} ${creator.country.name}", fontSize = 11.sp, color = InstagramTextSecondary)
                    }

                    Text("${creator.handle} • ${creator.category}", fontSize = 12.sp, color = InstagramTextSecondary)

                    if (creator.isLive) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            PulsingDot()
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("LIVE • ${creator.viewerCount} watching", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        }
                    }
                }
            }

            Button(
                onClick = onWatchStream,
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = if (creator.isLive) InstagramPink else InstagramTextDark),
                modifier = Modifier.height(36.dp)
            ) {
                Text(if (creator.isLive) "Watch" else "View", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
            }
        }
    }
}
