package com.metalonomicon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Using serif fonts for the gothic aesthetic
// In a production app, you would include custom fonts like UnicalAntiqua and EB Garamond
val UnciaTitleFont = FontFamily.Serif

// Scholarly serif font for body text
val GaramondBodyFont = FontFamily.Serif

val MetalonomiconTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = UnciaTitleFont,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        color = BloodRed
    ),
    displayMedium = TextStyle(
        fontFamily = UnciaTitleFont,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        color = BloodRed
    ),
    displaySmall = TextStyle(
        fontFamily = UnciaTitleFont,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
        color = BloodRed
    ),
    headlineLarge = TextStyle(
        fontFamily = UnciaTitleFont,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        color = BloodRed
    ),
    headlineMedium = TextStyle(
        fontFamily = UnciaTitleFont,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        color = BloodRed
    ),
    headlineSmall = TextStyle(
        fontFamily = UnciaTitleFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = BloodRed
    ),
    titleLarge = TextStyle(
        fontFamily = GaramondBodyFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = ParchmentTan
    ),
    titleMedium = TextStyle(
        fontFamily = GaramondBodyFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        color = ParchmentTan
    ),
    titleSmall = TextStyle(
        fontFamily = GaramondBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = ParchmentTan
    ),
    bodyLarge = TextStyle(
        fontFamily = GaramondBodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = ParchmentTan
    ),
    bodyMedium = TextStyle(
        fontFamily = GaramondBodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = ParchmentTan
    ),
    bodySmall = TextStyle(
        fontFamily = GaramondBodyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        color = ParchmentTan
    ),
    labelLarge = TextStyle(
        fontFamily = GaramondBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        color = ParchmentTan
    ),
    labelMedium = TextStyle(
        fontFamily = GaramondBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = ParchmentTan
    ),
    labelSmall = TextStyle(
        fontFamily = GaramondBodyFont,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        color = ParchmentTan
    )
)
