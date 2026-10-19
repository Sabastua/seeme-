package com.example.seeme.data.model

data class CreatorCountry(
    val code: String,
    val name: String,
    val flagEmoji: String
)

data class CreatorProfile(
    val id: String,
    val name: String,
    val handle: String,
    val country: CreatorCountry,
    val category: String,
    val isLive: Boolean = true,
    val viewerCount: Int = 0,
    val followers: String = "10K",
    val bio: String = "",
    val videoUrl: String,
    val likesCount: Long = 1200,
    val commentsCount: Long = 300,
    val isNewCreator: Boolean = false,
    val streamQualityScore: Float = 4.8f,
    val hookedAudiencePercent: Int = 92,
    val rankPosition: Int = 1
)
