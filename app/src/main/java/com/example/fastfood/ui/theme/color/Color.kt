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
    override val textPrimary: Color = ColorPalette.OnSurfaceLight
    override val background: Color = ColorPalette.BackgroundLight
    override val cardBackground: Color = ColorPalette.SurfaceLight
    override val primaryContainer: Color = ColorPalette.PrimaryContainerLight
    override val onPrimaryContainer: Color = ColorPalette.OnPrimaryContainerLight
    override val surface: Color = ColorPalette.SurfaceVariantLight
    override val primary: Color = ColorPalette.PrimaryLight
    override val onPrimary: Color = ColorPalette.OnPrimaryLight
    override val primaryDisabled: Color = ColorPalette.Gray150
    override val onPrimaryDisabled: Color = ColorPalette.OnSurfaceVariantLight
}


class DarkColorScheme : ColorScheme {
    override val textPrimary: Color = ColorPalette.OnSurfaceDark
    override val background: Color = ColorPalette.BackgroundDark
    override val cardBackground: Color = ColorPalette.SurfaceDark
    override val primaryContainer: Color = ColorPalette.PrimaryContainerDark
    override val onPrimaryContainer: Color = ColorPalette.OnPrimaryContainerDark
    override val surface: Color = ColorPalette.SurfaceVariantDark
    override val primary: Color = ColorPalette.PrimaryDark
    override val onPrimary: Color = ColorPalette.OnPrimaryDark
    override val primaryDisabled: Color = ColorPalette.Gray700
    override val onPrimaryDisabled: Color = ColorPalette.OnSurfaceVariantDark
}