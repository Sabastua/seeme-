package com.example.seeme.data.model

data class VideoItem(
    val id: String,
    val videoUrl: String,
    val creatorName: String,
    val creatorHandle: String,
    val description: String,
    val likesCount: Long,
    val commentsCount: Long,
    val sharesCount: Long,
    val isLive: Boolean = false,
    val viewerCount: Int = 0,
    val avatarUrl: String = ""
)
