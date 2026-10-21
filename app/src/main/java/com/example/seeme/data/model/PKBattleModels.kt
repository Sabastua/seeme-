package com.example.seeme.data.model

data class VirtualGift(
    val id: String,
    val name: String,
    val iconEmoji: String,
    val coinCost: Int,
    val bannerText: String
)

data class StreamerGoal(
    val title: String = "Daily Goal: Camera Upgrade 📷",
    val currentCoins: Int = 350,
    val targetCoins: Int = 500
) {
    val progressPercent: Float
        get() = (currentCoins.toFloat() / targetCoins.toFloat()).coerceIn(0f, 1f)
}

data class PKBattle(
    val id: String,
    val creator1: CreatorProfile,
    val creator2: CreatorProfile,
    val creator1Points: Int = 1850,
    val creator2Points: Int = 2100,
    val timeRemainingSeconds: Int = 240,
    val isEnded: Boolean = false
) {
    val totalPoints: Int
        get() = (creator1Points + creator2Points).coerceAtLeast(1)

    val creator1Ratio: Float
        get() = creator1Points.toFloat() / totalPoints.toFloat()

    val creator2Ratio: Float
        get() = creator2Points.toFloat() / totalPoints.toFloat()
}
