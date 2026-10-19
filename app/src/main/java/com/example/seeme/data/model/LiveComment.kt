package com.example.seeme.data.model

data class LiveComment(
    val id: String,
    val username: String,
    val userAvatar: String = "",
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)
