package com.example.seeme.data.model

object CoinConstants {
    const val COIN_USD_RATE = 2.20
    const val ADMIN_FEE_PERCENT = 0.60
    const val CREATOR_PAYOUT_PERCENT = 0.40
}

data class CoinPackage(
    val coinAmount: Int,
    val label: String
) {
    val grossUsd: Double
        get() = coinAmount * CoinConstants.COIN_USD_RATE

    val adminFeeUsd: Double
        get() = grossUsd * CoinConstants.ADMIN_FEE_PERCENT

    val creatorPayoutUsd: Double
        get() = grossUsd * CoinConstants.CREATOR_PAYOUT_PERCENT
}

data class CoinTransaction(
    val id: String,
    val creatorName: String,
    val creatorHandle: String,
    val coinAmount: Int,
    val timestamp: Long = System.currentTimeMillis()
) {
    val grossUsd: Double
        get() = coinAmount * CoinConstants.COIN_USD_RATE

    val adminFeeUsd: Double
        get() = grossUsd * CoinConstants.ADMIN_FEE_PERCENT

    val creatorPayoutUsd: Double
        get() = grossUsd * CoinConstants.CREATOR_PAYOUT_PERCENT
}

data class CoinWallet(
    val coinsAvailable: Int = 200,
    val coinsReceived: Int = 1250
) {
    val balanceUsd: Double
        get() = coinsAvailable * CoinConstants.COIN_USD_RATE

    val grossRevenueUsd: Double
        get() = coinsReceived * CoinConstants.COIN_USD_RATE

    val adminPlatformFeeUsd: Double
        get() = grossRevenueUsd * CoinConstants.ADMIN_FEE_PERCENT

    val netCreatorPayoutUsd: Double
        get() = grossRevenueUsd * CoinConstants.CREATOR_PAYOUT_PERCENT
}
