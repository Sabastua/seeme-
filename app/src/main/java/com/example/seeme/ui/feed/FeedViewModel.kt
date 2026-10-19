package com.example.seeme.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seeme.data.model.LiveComment
import com.example.seeme.data.model.VideoItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    private val _videoList = MutableStateFlow<List<VideoItem>>(emptyList())
    val videoList: StateFlow<List<VideoItem>> = _videoList.asStateFlow()

    private val _commentsMap = MutableStateFlow<Map<String, List<LiveComment>>>(emptyMap())
    val commentsMap: StateFlow<Map<String, List<LiveComment>>> = _commentsMap.asStateFlow()

    init {
        loadInitialData()
        startLiveCommentSimulation()
    }

    private fun loadInitialData() {
        val initialVideos = listOf(
            VideoItem(
                id = "live_1",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                creatorName = "Alex Riviera",
                creatorHandle = "@alex_live",
                description = "Live Coding session & Q&A! Join the stream 🚀 #android #live",
                likesCount = 12400,
                commentsCount = 389,
                sharesCount = 152,
                isLive = true,
                viewerCount = 2840
            ),
            VideoItem(
                id = "live_2",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
                creatorName = "Maya Lin",
                creatorHandle = "@maya_arts",
                description = "Creating 3D digital art live on stream 🎨✨",
                likesCount = 8900,
                commentsCount = 210,
                sharesCount = 94,
                isLive = true,
                viewerCount = 1250
            ),
            VideoItem(
                id = "video_3",
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                creatorName = "TechPulse",
                creatorHandle = "@techpulse",
                description = "Top 5 features in Jetpack Compose 2026! 🔥",
                likesCount = 45200,
                commentsCount = 1040,
                sharesCount = 820,
                isLive = false
            )
        )

        _videoList.value = initialVideos

        _commentsMap.value = mapOf(
            "live_1" to listOf(
                LiveComment("c1", "DevDan", "", "Awesome stream! 🔥"),
                LiveComment("c2", "Sarah_K", "", "Can you explain state hoisting again?"),
                LiveComment("c3", "TechBro", "", "SeeMe looks incredible!")
            ),
            "live_2" to listOf(
                LiveComment("c4", "ArtFan99", "", "So beautiful! 🎨"),
                LiveComment("c5", "Leo", "", "What tool are you using?")
            )
        )
    }

    fun addComment(videoId: String, message: String) {
        val newComment = LiveComment(
            id = System.currentTimeMillis().toString(),
            username = "You",
            message = message
        )

        _commentsMap.update { current ->
            val existing = current[videoId] ?: emptyList()
            current + (videoId to (existing + newComment))
        }

        // Increment comment count
        _videoList.update { list ->
            list.map {
                if (it.id == videoId) it.copy(commentsCount = it.commentsCount + 1) else it
            }
        }
    }

    fun likeVideo(videoId: String) {
        _videoList.update { list ->
            list.map {
                if (it.id == videoId) it.copy(likesCount = it.likesCount + 1) else it
            }
        }
    }

    private fun startLiveCommentSimulation() {
        val sampleUsers = listOf("Jordan", "Elena_R", "CodeNinja", "Sam_W", "Chloe", "DevRider")
        val sampleMessages = listOf(
            "Love this content! ❤️",
            "Greetings from SF! 👋",
            "This app is so fast!",
            "Double tapped for heart reactions 🔥",
            "Keep it up!! 🎉",
            "Super clean UI design!"
        )

        viewModelScope.launch {
            while (true) {
                delay(3500)
                val liveVideos = _videoList.value.filter { it.isLive }
                if (liveVideos.isNotEmpty()) {
                    val targetVideo = liveVideos.random()
                    val randomComment = LiveComment(
                        id = System.currentTimeMillis().toString(),
                        username = sampleUsers.random(),
                        message = sampleMessages.random()
                    )

                    _commentsMap.update { map ->
                        val existing = map[targetVideo.id] ?: emptyList()
                        map + (targetVideo.id to (existing.takeLast(25) + randomComment))
                    }
                }
            }
        }
    }
}
