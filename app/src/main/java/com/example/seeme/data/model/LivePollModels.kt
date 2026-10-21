package com.example.seeme.data.model

data class PollOption(
    val id: String,
    val text: String,
    val votes: Int = 0
)

data class LivePoll(
    val id: String = "poll_1",
    val question: String = "What app topic should we build next on stream?",
    val options: List<PollOption> = listOf(
        PollOption("o1", "A) Jetpack Compose Animation", 142),
        PollOption("o2", "B) Kotlin Multiplatform Mobile", 98),
        PollOption("o3", "C) AI Real-Time Streaming", 210)
    )
) {
    val totalVotes: Int
        get() = options.sumOf { it.votes }.coerceAtLeast(1)
}

data class PinnedQA(
    val id: String = "qa_1",
    val askerName: String = "DevDan",
    val question: String = "How do you handle sub-second latency for live PK battles?",
    val upvotes: Int = 45,
    val isPinned: Boolean = true
)
