package com.example.seeme.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val PureWhiteGlassColorScheme = lightColorScheme(
    primary = InstagramTextDark,
    onPrimary = Color.White,
    primaryContainer = SmokeWhiteCard,
    onPrimaryContainer = InstagramTextDark,
    secondary = InstagramTextSecondary,
    onSecondary = Color.White,
    background = SmokeWhiteBg,
    onBackground = InstagramTextDark,
    surface = SmokeWhiteSurface,
    onSurface = InstagramTextDark,
    surfaceVariant = SmokeWhiteCard,
    onSurfaceVariant = InstagramTextDark,
    outline = SmokeWhiteBorder
)

@Composable
fun SeemeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PureWhiteGlassColorScheme,
        typography = Typography,
        content = content
    )
}
