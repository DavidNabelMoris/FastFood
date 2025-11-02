package com.example.fastfood

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fastfood.list.composable.RestoItem
import com.example.fastfood.model.FastFood

import com.example.fastfood.model.HoraireJour
import com.example.fastfood.ui.theme.FastFoodTheme

class RestoItemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fastfood = intent.getParcelableExtra<FastFood>("fastfood")

        setContent {
            FastFoodTheme {
                if (fastfood != null) {
                    RestoItem(fastfood)
                }
            }
        }
    }
}

