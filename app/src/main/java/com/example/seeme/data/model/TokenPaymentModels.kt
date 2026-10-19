package com.example.seeme.data.model

data class CurrencyOption(
    val code: String,
    val name: String,
    val symbol: String,
    val flagEmoji: String,
    val ratePerToken: Double
)

data class PaymentGateway(
    val id: String,
    val name: String,
    val subtitle: String,
    val iconEmoji: String
)

data class TokenPackage(
    val tokenAmount: Int
) {
    fun getLocalPrice(currency: CurrencyOption): Double {
        return tokenAmount * (currency.ratePerToken / 1.0)
    }

    fun formatLocalPrice(currency: CurrencyOption): String {
        val price = getLocalPrice(currency)
        return when (currency.code) {
            "JPY" -> "${currency.symbol}${price.toInt()}"
            "KES", "NGN" -> "${currency.symbol}${String.format("%.0f", price)}"
            else -> "${currency.symbol}${String.format("%.2f", price)}"
        }
    }
}
