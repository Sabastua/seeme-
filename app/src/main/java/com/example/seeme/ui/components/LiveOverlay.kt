package com.example.seeme.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seeme.data.model.LiveComment
import com.example.seeme.data.model.VideoItem
import com.example.seeme.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

data class FloatingHeart(
    val id: Long,
    val xOffsetRatio: Float,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveOverlay(
    videoItem: VideoItem,
    comments: List<LiveComment>,
    modifier: Modifier = Modifier,
    onSendComment: (String) -> Unit = {},
    onLikeClicked: () -> Unit = {},
    onShareClicked: () -> Unit = {},
    onFollowClicked: () -> Unit = {},
    onTipClicked: () -> Unit = {},
    onOpenPoll: () -> Unit = {},
    onOpenVIP: () -> Unit = {}
) {
    var commentText by remember { mutableStateOf("") }
    val floatingHearts = remember { mutableStateListOf<FloatingHeart>() }
    val listState = rememberLazyListState()
    val haptic = LocalHapticFeedback.current

    // Auto scroll comments to bottom when new comment arrives
    LaunchedEffect(comments.size) {
        if (comments.isNotEmpty()) {
            listState.animateScrollToItem(comments.size - 1)
        }
    }

    fun triggerHeartReaction() {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
        onLikeClicked()
        val heartColors = listOf(Color(0xFFFF2D55), Color(0xFFFF9500), Color(0xFFFFCC00), Color(0xFF4CD964), Color(0xFF5AC8FA), Color(0xFFFF2D55))
        floatingHearts.add(
            FloatingHeart(
                id = System.currentTimeMillis() + Random.nextLong(1000),
                xOffsetRatio = Random.nextFloat() * 0.4f - 0.2f,
                color = heartColors.random()
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { triggerHeartReaction() }
                )
            }
    ) {
        floatingHearts.forEach { heart ->
            key(heart.id) {
                AnimatedHeart(
                    heart = heart,
                    onAnimationComplete = { floatingHearts.remove(heart) }
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar: Creator Info & Live Indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.45f), CircleShape)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = videoItem.creatorName.take(1).uppercase(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            text = videoItem.creatorName,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = videoItem.creatorHandle,
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onFollowClicked()
                        },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(28.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(text = "Follow", fontSize = 12.sp, color = Color.White)
                    }
                }

                if (videoItem.isLive) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color.Red, CircleShape)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        PulsingDot()
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "LIVE",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${videoItem.viewerCount}",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // Bottom Section: Comments & Action Column
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false),
                verticalAlignment = Alignment.Bottom
            ) {
                // Live Comments Stream (Left side)
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(max = 220.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(comments, key = { it.id }) { comment ->
                        Row(
                            modifier = Modifier
                                .shadow(2.dp, RoundedCornerShape(14.dp))
                                .background(SmokeWhiteSurface.copy(alpha = 0.92f), RoundedCornerShape(14.dp))
                                .border(1.dp, SmokeWhiteBorder, RoundedCornerShape(14.dp))
                                .padding(horizontal = 12.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = "${comment.username}: ",
                                color = InstagramPink,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = comment.message,
                                color = InstagramTextDark,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Side Action Buttons (Right side)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // VIP Fan Club Action
                    IconButtonWithBadge(
                        icon = Icons.Filled.Star,
                        tint = CoinGold,
                        count = "VIP",
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onOpenVIP()
                        }
                    )

                    // Live Poll Action
                    IconButtonWithBadge(
                        icon = Icons.Filled.HowToVote,
                        tint = Color(0xFF007AFF),
                        count = "Poll",
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onOpenPoll()
                        }
                    )

                    // Tip Coins Action
                    IconButtonWithBadge(
                        icon = Icons.Filled.MonetizationOn,
                        tint = CoinGold,
                        count = "Tip",
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onTipClicked()
                        }
                    )

                    // Like Action
                    IconButtonWithBadge(
                        icon = Icons.Filled.Favorite,
                        tint = Color(0xFFFF2D55),
                        count = "${videoItem.likesCount}",
                        onClick = { triggerHeartReaction() }
                    )

                    // Share Action
                    IconButtonWithBadge(
                        icon = Icons.Filled.Share,
                        tint = Color.White,
                        count = "${videoItem.sharesCount}",
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onShareClicked()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom Comment Input Field
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    placeholder = { Text("Add a comment...", color = Color.White.copy(alpha = 0.6f)) },
                    modifier = Modifier
                        .weight(1f)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.45f)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (commentText.isNotBlank()) {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onSendComment(commentText)
                            commentText = ""
                        }
                    },
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun IconButtonWithBadge(
    icon: ImageVector,
    tint: Color,
    count: String,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.25f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "scale"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = {
                isPressed = true
                onClick()
            },
            modifier = Modifier
                .scale(scale)
                .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                .size(44.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = tint)
        }

        LaunchedEffect(isPressed) {
            if (isPressed) {
                delay(150)
                isPressed = false
            }
        }

        Text(
            text = count,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun PulsingDot() {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(8.dp)
            .scale(scale)
            .background(Color.White, CircleShape)
    )
}

@Composable
fun AnimatedHeart(
    heart: FloatingHeart,
    onAnimationComplete: () -> Unit
) {
    val animProgress = remember { Animatable(0f) }

    LaunchedEffect(heart.id) {
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1800, easing = LinearOutSlowInEasing)
        )
        onAnimationComplete()
    }

    val yOffset = (1f - animProgress.value) * 320.dp.value
    val alpha = 1f - animProgress.value
    val scale = 0.5f + (animProgress.value * 0.9f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = heart.color.copy(alpha = alpha),
            modifier = Modifier
                .offset(
                    x = (heart.xOffsetRatio * 200).dp,
                    y = -yOffset.dp
                )
                .scale(scale)
                .size(38.dp)
        )
    }
}
