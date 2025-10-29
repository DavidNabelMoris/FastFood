package com.example.fastfood.ui.theme.color

import androidx.compose.ui.graphics.Color

interface ColorScheme {
    val textPrimary: Color
    val background: Color
    val cardBackground: Color
    val primaryContainer: Color
    val onPrimaryContainer: Color
    val surface: Color
    val primary: Color
    val onPrimary: Color
    val primaryDisabled: Color
    val onPrimaryDisabled: Color
}

class LightColorScheme : ColorScheme {
    override val textPrimary: Color = ColorPalette.Gray900
    override val background: Color = ColorPalette.Gray500
    override val cardBackground: Color = ColorPalette.White
    override val primaryContainer: Color = ColorPalette.Green400
    override val onPrimaryContainer: Color = ColorPalette.Gray700
    override val surface: Color = ColorPalette.Gray400
    override val primary: Color = ColorPalette.Green600
    override val onPrimary: Color = ColorPalette.White
    override val primaryDisabled: Color = ColorPalette.Gray500
    override val onPrimaryDisabled: Color = ColorPalette.Gray600
}

class DarkColorScheme : ColorScheme {
    override val textPrimary: Color = ColorPalette.White
    override val background: Color = ColorPalette.Gray800
    override val cardBackground: Color = ColorPalette.Gray700
    override val primaryContainer: Color = ColorPalette.Green700
    override val onPrimaryContainer: Color = ColorPalette.Green400
    override val surface: Color = ColorPalette.Gray600
    override val primary: Color = ColorPalette.Green500
    override val onPrimary: Color = ColorPalette.Green800
    override val primaryDisabled: Color = ColorPalette.Gray650
    override val onPrimaryDisabled: Color = ColorPalette.Gray550
}