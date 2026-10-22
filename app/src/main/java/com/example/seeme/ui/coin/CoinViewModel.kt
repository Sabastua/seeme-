package com.example.seeme.ui.coin

import androidx.lifecycle.ViewModel
import com.example.seeme.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CoinViewModel : ViewModel() {

    private val _wallet = MutableStateFlow(CoinWallet(coinsAvailable = 250, coinsReceived = 1450))
    val wallet: StateFlow<CoinWallet> = _wallet.asStateFlow()

    private val _transactions = MutableStateFlow<List<CoinTransaction>>(emptyList())
    val transactions: StateFlow<List<CoinTransaction>> = _transactions.asStateFlow()

    val availablePackages = listOf(
        CoinPackage(1, "1 Coin ($2.20)"),
        CoinPackage(5, "5 Coins ($11.00)"),
        CoinPackage(10, "10 Coins ($22.00)"),
        CoinPackage(50, "50 Coins ($110.00)"),
        CoinPackage(100, "100 Coins ($220.00)")
    )

    val supportedCurrencies = listOf(
        CurrencyOption("USD", "US Dollar", "$", "🇺🇸", 2.20),
        CurrencyOption("KES", "Kenyan Shilling", "KSh ", "🇰🇪", 286.0),
        CurrencyOption("NGN", "Nigerian Naira", "₦", "🇳🇬", 3300.0),
        CurrencyOption("EUR", "Euro", "€", "🇪🇺", 2.02),
        CurrencyOption("GBP", "British Pound", "£", "🇬🇧", 1.72),
        CurrencyOption("JPY", "Japanese Yen", "¥", "🇯🇵", 330.0),
        CurrencyOption("BRL", "Brazilian Real", "R$ ", "🇧🇷", 11.00)
    )

    val paymentGateways = listOf(
        PaymentGateway("mpesa", "M-Pesa STK Push API", "Instant Mobile Money STK Push (Kenya 🇰🇪)", "📱"),
        PaymentGateway("card", "Credit / Debit Card", "Visa, Mastercard, Amex Global", "💳"),
        PaymentGateway("paystack", "Paystack / Bank Transfer", "Direct bank debit for Nigeria & West Africa 🇳🇬", "⚡"),
        PaymentGateway("pix", "Pix Instant Payment", "Instant QR payment for Brazil 🇧🇷", "🇧🇷"),
        PaymentGateway("digital_wallet", "Google Pay / Apple Pay", "One-tap global wallet payment", "🌐")
    )

    val tokenPackages = listOf(
        TokenPackage(10),
        TokenPackage(50),
        TokenPackage(100),
        TokenPackage(500),
        TokenPackage(1000)
    )

    fun sendTip(creatorName: String, creatorHandle: String, coinPackage: CoinPackage): Boolean {
        if (_wallet.value.coinsAvailable < coinPackage.coinAmount) {
            return false
        }

        val newTransaction = CoinTransaction(
            id = System.currentTimeMillis().toString(),
            creatorName = creatorName,
            creatorHandle = creatorHandle,
            coinAmount = coinPackage.coinAmount
        )

        _wallet.update {
            it.copy(
                coinsAvailable = it.coinsAvailable - coinPackage.coinAmount,
                coinsReceived = it.coinsReceived + coinPackage.coinAmount
            )
        }

        _transactions.update { listOf(newTransaction) + it }
        return true
    }

    fun topUpCoins(amount: Int) {
        _wallet.update {
            it.copy(coinsAvailable = it.coinsAvailable + amount)
        }
    }

    fun processTokenPurchase(
        tokenPackage: TokenPackage,
        paymentGateway: PaymentGateway,
        currency: CurrencyOption
    ) {
        _wallet.update {
            it.copy(coinsAvailable = it.coinsAvailable + tokenPackage.tokenAmount)
        }
    }
}
