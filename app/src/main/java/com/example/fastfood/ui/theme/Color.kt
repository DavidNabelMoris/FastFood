package com.example.fastfood.ui.theme

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
object ColorPalette {
    val Gray900 = Color(0xFF0E0E10)
    val Gray800 = Color(0xFF1B1A1B)
    val Gray700 = Color(0xFF282828)
    val Gray670 = Color(0xFF92968E)
    val Gray650 = Color(0xFF2B2D29)
    val Gray600 = Color(0xFF353535)
    val Gray550 = Color(0xFFDDDFD8)
    val Gray500 = Color(0xFFF0F2F5)
    val Gray400 = Color(0xFFF5F5F5)

    val Green800 = Color(0xFF083910)
    val Green700 = Color(0xFF235024)
    val Green600 = Color(0xFF3A693A)
    val Green500 = Color(0xFFA0D49B)
    val Green400 = Color(0xFFBBF0B5)

    val White = Color(0xFFFFFFFF)
}
