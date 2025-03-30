package com.example.attendancetrackerapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

private val LightColorScheme = lightColorScheme(
    primary = LightPurple,
    secondary = LightPurple,
    background = White,
    surface = White,
    onPrimary = White,
    onBackground = DarkGrey
)

private val DarkColorScheme = darkColorScheme(
    primary = LightPink,
    secondary = LightPink,
    background = Black,
    surface = Black,
    onPrimary = Black,
    onBackground = White
)

object ThemeProviders {
    @SuppressLint("CompositionLocalNaming")
    val DarkThemeState = staticCompositionLocalOf<Boolean> { false }
}

@Composable
fun AttendanceTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    ThemeProviders.DarkThemeState provides darkTheme
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}