package com.example.seeme.ui.pk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seeme.data.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PKViewModel : ViewModel() {

    val virtualGifts = listOf(
        VirtualGift("g1", "Rose", "🌹", 1, "sent a Rose 🌹"),
        VirtualGift("g2", "Rocket Launch", "🚀", 10, "launched a Rocket! 🚀"),
        VirtualGift("g3", "Golden Crown", "👑", 50, "crowned the Streamer! 👑"),
        VirtualGift("g4", "Majestic Lion", "🦁", 100, "sent a Majestic Lion! 🦁"),
        VirtualGift("g5", "Galaxy Burst", "🌌", 500, "triggered a Galaxy Burst! 🌌")
    )

    private val _streamerGoal = MutableStateFlow(StreamerGoal(title = "Daily Goal: Camera Upgrade 📷", currentCoins = 380, targetCoins = 500))
    val streamerGoal: StateFlow<StreamerGoal> = _streamerGoal.asStateFlow()

    private val _activeBattle = MutableStateFlow<PKBattle?>(null)
    val activeBattle: StateFlow<PKBattle?> = _activeBattle.asStateFlow()

    private val _lastSentGiftBanner = MutableStateFlow<String?>(null)
    val lastSentGiftBanner: StateFlow<String?> = _lastSentGiftBanner.asStateFlow()

    init {
        initBattle()
        startTimer()
    }

    private fun initBattle() {
        val usa = CreatorCountry("US", "USA", "🇺🇸")
        val kenya = CreatorCountry("KE", "Kenya", "🇰🇪")

        val creator1 = CreatorProfile(
            id = "c1",
            name = "Alex Riviera",
            handle = "@alex_live",
            country = usa,
            category = "Coding",
            isLive = true,
            viewerCount = 2840,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        )

        val creator2 = CreatorProfile(
            id = "c2",
            name = "Amina Wanjiku",
            handle = "@amina_tech",
            country = kenya,
            category = "Tech",
            isLive = true,
            viewerCount = 1950,
            videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        )

        _activeBattle.value = PKBattle(
            id = "pk_101",
            creator1 = creator1,
            creator2 = creator2,
            creator1Points = 2100,
            creator2Points = 2450,
            timeRemainingSeconds = 180,
            isEnded = false
        )
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _activeBattle.update { battle ->
                    if (battle == null || battle.isEnded) battle
                    else {
                        val newTime = battle.timeRemainingSeconds - 1
                        if (newTime <= 0) battle.copy(timeRemainingSeconds = 0, isEnded = true)
                        else battle.copy(timeRemainingSeconds = newTime)
                    }
                }
            }
        }
    }

    fun sendVirtualGift(creatorId: String, gift: VirtualGift) {
        // Update battle points
        _activeBattle.update { battle ->
            if (battle == null) null
            else if (creatorId == battle.creator1.id) {
                battle.copy(creator1Points = battle.creator1Points + (gift.coinCost * 10))
            } else {
                battle.copy(creator2Points = battle.creator2Points + (gift.coinCost * 10))
            }
        }

        // Update goal progress
        _streamerGoal.update { goal ->
            goal.copy(currentCoins = goal.currentCoins + gift.coinCost)
        }

        // Trigger gift banner
        _lastSentGiftBanner.value = "${gift.iconEmoji} ${gift.name} (${gift.coinCost} Coins)"
        viewModelScope.launch {
            delay(3000)
            _lastSentGiftBanner.value = null
        }
    }
}
