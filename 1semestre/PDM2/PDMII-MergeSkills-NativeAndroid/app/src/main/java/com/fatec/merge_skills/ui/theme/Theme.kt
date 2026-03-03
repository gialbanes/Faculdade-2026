package com.fatec.merge_skills.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val MergeSkillsColorScheme = darkColorScheme(
    primary = StitchGreen,
    onPrimary = Color.Black,
    primaryContainer = StitchGreenContainer,
    onPrimaryContainer = Color.White,
    secondary = StitchGreenDark,
    onSecondary = Color.White,
    background = Background,
    onBackground = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurface,
    error = StitchError,
    onError = Color.White
)

@Composable
fun MergeskillskotlinTheme(
    // Merge Skills is Dark Mode by design
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MergeSkillsColorScheme,
        typography = Typography,
        content = content
    )
}