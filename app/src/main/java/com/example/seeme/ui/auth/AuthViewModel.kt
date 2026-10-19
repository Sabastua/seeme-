package com.example.seeme.ui.auth

import androidx.lifecycle.ViewModel
import com.example.seeme.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {

    private val _currentUser = MutableStateFlow(UserProfile(isGuest = true))
    val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

    fun signUpCreator(
        name: String,
        handle: String,
        email: String,
        countryFlag: String = "🇰🇪",
        category: String = "Coding",
        bio: String = "Creator & Live Streamer on SeeMe 🎥"
    ) {
        val cleanHandle = if (handle.startsWith("@")) handle else "@$handle"
        _currentUser.value = UserProfile(
            id = System.currentTimeMillis().toString(),
            name = name.ifBlank { "Creator" },
            handle = cleanHandle.ifBlank { "@creator_live" },
            email = email,
            countryFlag = countryFlag,
            category = category,
            bio = bio,
            isGuest = false,
            isBroadcasterVerified = true
        )
    }

    fun signIn(email: String) {
        val nameFromEmail = email.substringBefore("@").replace(".", " ").capitalize()
        val handleFromEmail = "@${email.substringBefore("@")}"
        _currentUser.value = UserProfile(
            id = System.currentTimeMillis().toString(),
            name = nameFromEmail,
            handle = handleFromEmail,
            email = email,
            isGuest = false,
            isBroadcasterVerified = true
        )
    }

    fun signOutToGuest() {
        _currentUser.value = UserProfile(isGuest = true)
    }
}
