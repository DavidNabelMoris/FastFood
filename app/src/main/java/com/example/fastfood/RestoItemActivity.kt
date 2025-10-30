package com.example.fastfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fastfood.list.composable.RestoItem

import com.example.fastfood.model.HoraireJour


class RestoItemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nom = intent.getStringExtra("nom") ?: ""
        val address = intent.getStringExtra("address") ?: ""
        val note = intent.getFloatExtra("note", 0f)
        val favoris = intent.getBooleanExtra("favoris", false)
        val horaires: List<HoraireJour> =
            intent.getParcelableArrayListExtra<HoraireJour>("horaires") ?: emptyList()

        setContent {
            RestoItem(nom, address, note, favoris, horaires)
        }
    }
}
