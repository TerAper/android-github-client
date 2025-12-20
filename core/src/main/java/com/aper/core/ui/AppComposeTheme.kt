package com.aper.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.aper.core.model.AppTheme

private val LightColors = lightColorScheme(
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    primary = Color(0xFF6200EE),
    onPrimary = Color.White
)

private val DarkColors = darkColorScheme(
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black
)

@Composable
fun AppComposeTheme(
    theme: AppTheme,
    content: @Composable () -> Unit
) {
    val darkTheme = when (theme) {
        AppTheme.DARK -> true
        AppTheme.LIGHT -> false
        AppTheme.SYSTEM -> isSystemInDarkTheme()
    }

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography(),
        content = content
    )
}
