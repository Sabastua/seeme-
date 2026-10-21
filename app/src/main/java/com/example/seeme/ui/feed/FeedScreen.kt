package com.example.seeme.ui.feed

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.seeme.data.model.LivePoll
import com.example.seeme.data.model.VideoItem
import com.example.seeme.ui.coin.CoinViewModel
import com.example.seeme.ui.components.CoinTippingDialog
import com.example.seeme.ui.components.LiveOverlay
import com.example.seeme.ui.components.LivePollDialog
import com.example.seeme.ui.components.VIPClubDialog
import com.example.seeme.ui.components.VideoPlayer

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = viewModel(),
    coinViewModel: CoinViewModel = viewModel()
) {
    val videos by viewModel.videoList.collectAsState()
    val commentsMap by viewModel.commentsMap.collectAsState()
    val wallet by coinViewModel.wallet.collectAsState()

    var showTippingDialogForVideo by remember { mutableStateOf<VideoItem?>(null) }
    var showPollDialogForVideo by remember { mutableStateOf<VideoItem?>(null) }
    var showVIPDialogForVideo by remember { mutableStateOf<VideoItem?>(null) }

    val activePoll = remember { LivePoll() }

    if (videos.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
        )
        return
    }

    val pagerState = rememberPagerState(pageCount = { videos.size })

    Box(modifier = modifier.fillMaxSize()) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) { page ->
            val videoItem = videos[page]
            val comments = commentsMap[videoItem.id] ?: emptyList()

            Box(modifier = Modifier.fillMaxSize()) {
                VideoPlayer(
                    videoUrl = videoItem.videoUrl,
                    modifier = Modifier.fillMaxSize()
                )

                LiveOverlay(
                    videoItem = videoItem,
                    comments = comments,
                    onSendComment = { text ->
                        viewModel.addComment(videoItem.id, text)
                    },
                    onLikeClicked = {
                        viewModel.likeVideo(videoItem.id)
                    },
                    onTipClicked = {
                        showTippingDialogForVideo = videoItem
                    },
                    onOpenPoll = {
                        showPollDialogForVideo = videoItem
                    },
                    onOpenVIP = {
                        showVIPDialogForVideo = videoItem
                    }
                )
            }
        }

        // Coin Tipping Modal Sheet
        showTippingDialogForVideo?.let { videoItem ->
            CoinTippingDialog(
                videoItem = videoItem,
                availableCoins = wallet.coinsAvailable,
                packages = coinViewModel.availablePackages,
                onDismiss = { showTippingDialogForVideo = null },
                onSendTip = { pkg ->
                    val success = coinViewModel.sendTip(videoItem.creatorName, videoItem.creatorHandle, pkg)
                    if (success) {
                        viewModel.addComment(
                            videoItem.id,
                            "🪙 Tipped ${pkg.coinAmount} coins ($${String.format("%.2f", pkg.grossUsd)})!"
                        )
                    }
                    showTippingDialogForVideo = null
                }
            )
        }

        // Live Poll Dialog
        showPollDialogForVideo?.let { videoItem ->
            LivePollDialog(
                poll = activePoll,
                onDismiss = { showPollDialogForVideo = null },
                onVoteOption = { optId ->
                    viewModel.addComment(videoItem.id, "📊 Voted in Live Poll!")
                    showPollDialogForVideo = null
                }
            )
        }

        // VIP Fan Club Dialog
        showVIPDialogForVideo?.let { videoItem ->
            VIPClubDialog(
                creatorName = videoItem.creatorName,
                onDismiss = { showVIPDialogForVideo = null },
                onSubscribe = {
                    viewModel.addComment(videoItem.id, "🌟 Joined ${videoItem.creatorName}'s VIP Fan Club!")
                    showVIPDialogForVideo = null
                }
            )
        }
    }
}
