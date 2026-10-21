package com.example.seeme.data.model

data class LiveKitRoomConfig(
    val serverUrl: String = "wss://live.seeme.app",
    val roomName: String,
    val participantToken: String = "token_sample_123",
    val subSecondLatencyMs: Int = 180,
    val isConnected: Boolean = true,
    val audioEnabled: Boolean = true,
    val videoEnabled: Boolean = true
)

object LiveKitRoomManager {
    fun createRoomConfig(roomName: String): LiveKitRoomConfig {
        return LiveKitRoomConfig(
            roomName = roomName,
            participantToken = "jwt_token_${System.currentTimeMillis()}"
        )
    }
}
