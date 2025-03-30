package com.example.attendancetrackerapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80
)

private val DarkColorScheme = darkColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40
)

@Composable
fun AttendanceTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content
    )
}