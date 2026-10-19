package com.example.seeme.data.model

data class UserProfile(
    val id: String = "guest_user",
    val name: String = "Guest Viewer",
    val handle: String = "@guest_viewer",
    val email: String = "",
    val countryCode: String = "US",
    val countryFlag: String = "🇺🇸",
    val category: String = "Viewer",
    val bio: String = "Browsing live streams on SeeMe 🚀",
    val isGuest: Boolean = true,
    val isBroadcasterVerified: Boolean = false
)
