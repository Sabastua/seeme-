package com.example.seeme.ui.translation

import com.example.seeme.data.model.LiveComment

data class TranslatedComment(
    val original: LiveComment,
    val translatedMessage: String,
    val sourceLanguage: String,
    val isTranslated: Boolean = true
)

object TranslationService {

    val supportedLanguages = listOf(
        "EN" to "English 🇺🇸",
        "SW" to "Swahili 🇰🇪",
        "JA" to "Japanese 🇯🇵",
        "PT" to "Portuguese 🇧🇷",
        "ES" to "Spanish 🇪🇸",
        "FR" to "French 🇫🇷"
    )

    private val translationDictionary = mapOf(
        "Habari gani rafiki!" to ("Swahili" to "How are you my friend!"),
        "Kazi safi sana!" to ("Swahili" to "Very clean work!"),
        "Konnichiwa! Sugoi stream!" to ("Japanese" to "Hello! Amazing stream!"),
        "Muito bom esse app!" to ("Portuguese" to "Very good this app!"),
        "Excelente transmisión!" to ("Spanish" to "Excellent broadcast!")
    )

    fun translate(comment: LiveComment): TranslatedComment {
        val entry = translationDictionary[comment.message]
        return if (entry != null) {
            TranslatedComment(
                original = comment,
                translatedMessage = entry.second,
                sourceLanguage = entry.first,
                isTranslated = true
            )
        } else {
            TranslatedComment(
                original = comment,
                translatedMessage = comment.message,
                sourceLanguage = "Auto",
                isTranslated = false
            )
        }
    }
}
