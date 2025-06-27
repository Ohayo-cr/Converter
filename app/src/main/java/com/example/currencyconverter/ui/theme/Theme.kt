package com.example.currencyconverter.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


@Composable
fun CurrencyConverterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    systemUiColor: Color? = null,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme(
            primary = Color(0xFF101624),
            onPrimary = Color(0xFFF5F7FA),
            secondary = Color(0xFF3D4966),
            onSecondary = Color(0xFFF5F7FA),
            background = Color(0xFF1A2035),
            onBackground = Color(0xFFEDEFF4),
            surface = Color(0xFF1C2338),
            onSurface = Color(0xFFBEC5D4),
            tertiary = Color(0xFF4A5772),
            onTertiary = Color(0xFFF5F7FA),
            error = Color(0xFFD63031),
            onError = Color.White,
            inversePrimary = Color(0xFF00A896),
            surfaceVariant = Color(0xFF424242)
        )
        else -> lightColorScheme(
            primary = Color(0xFFC9D6D9),
            onPrimary = Color(0xFF1F2937),
            secondary = Color(0xFFC9D6D9),
            onSecondary = Color(0xFF1F2937),
            background = Color.White,
            onBackground = Color(0xFF1F2937),
            surface = Color(0xFFC9D6D9),
            onSurface = Color(0xFF475569),
            tertiary = Color(0xFFE2E8F0),
            onTertiary = Color(0xFF475569),
            error = Color(0xFFD63031),
            onError = Color.White,
            inversePrimary = Color(0xFF3DABDE),
            surfaceVariant = Color(0xFFDBDBDB),

        )
    }
    val view = LocalView.current
    val systemBarsColor = systemUiColor ?: colorScheme.surface

    if (!view.isInEditMode) {
        SideEffect {
            val context = view.context
            if (context is Activity) {
                val window = context.window
                window.statusBarColor = systemBarsColor.toArgb()
                window.navigationBarColor = systemBarsColor.toArgb()
                val insetsController = WindowCompat.getInsetsController(window, view)
                insetsController.isAppearanceLightStatusBars = !darkTheme
                insetsController.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}