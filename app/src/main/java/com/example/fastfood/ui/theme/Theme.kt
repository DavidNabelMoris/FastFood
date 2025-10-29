package com.example.fastfood.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.example.fastfood.ui.theme.color.ColorPalette
import com.example.fastfood.ui.theme.color.ColorScheme
import com.example.fastfood.ui.theme.color.DarkColorScheme
import com.example.fastfood.ui.theme.color.LightColorScheme
import com.example.fastfood.ui.theme.typo.CoreTypo


val LocalTypo: ProvidableCompositionLocal<CoreTypo> = staticCompositionLocalOf { error("Typo not provided") }
val LocalColor: ProvidableCompositionLocal<ColorScheme> = staticCompositionLocalOf { error("Color not provided") }


@Composable
fun FastFoodTheme(
    content: @Composable () -> Unit,
) {
    val isSystemInDarkTheme: Boolean = isSystemInDarkTheme()
    val colors =
        if (isSystemInDarkTheme)
            DarkColorScheme()
        else
            LightColorScheme()

    CompositionLocalProvider(
        LocalColor provides colors,
        LocalTypo provides CoreTypo,
    ) {
        MaterialTheme {
            content()
        }
    }
}

object FastFoodTheme {
    val colors: ColorScheme
        @Composable
        get() = LocalColor.current


    val typography: CoreTypo
        @Composable
        get() = LocalTypo.current
}