package com.example.fastfood

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fastfood.list.composable.RestoItem
import com.example.fastfood.model.FastFood

import com.example.fastfood.model.HoraireJour


class RestoItemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val food: FastFood? = intent.getParcelableExtra("fastfood")

        setContent {
            if (food != null) {
                RestoItem(food)
            }
        }
    }
}
