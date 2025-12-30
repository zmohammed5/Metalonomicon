package com.metalonomicon.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MetalonomiconColorScheme = darkColorScheme(
    primary = BloodRed,
    onPrimary = ParchmentTan,
    primaryContainer = DarkRed,
    onPrimaryContainer = LighterTan,

    secondary = ParchmentTan,
    onSecondary = DeepCharcoalBlack,
    secondaryContainer = DarkGray,
    onSecondaryContainer = ParchmentTan,

    tertiary = BloodRed,
    onTertiary = Color.White,

    background = DeepCharcoalBlack,
    onBackground = ParchmentTan,

    surface = DarkerGray,
    onSurface = ParchmentTan,
    surfaceVariant = DarkGray,
    onSurfaceVariant = ParchmentTan,

    error = BloodRed,
    onError = Color.White,

    outline = DarkGray,
    outlineVariant = DarkerGray
)

@Composable
fun MetalonomiconTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MetalonomiconColorScheme,
        typography = MetalonomiconTypography,
        content = content
    )
}
