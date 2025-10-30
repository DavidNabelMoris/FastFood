package com.example.fastfood.list.composable

import android.graphics.Color.green
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastfood.model.HoraireJour

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestoItem(
    nom: String,
    adresse: String,
    note: Float,
    favoris: Boolean,
    horaires: List<HoraireJour>
) {
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Détails du restaurant") },
            navigationIcon = {
                IconButton(onClick = { backDispatcher?.onBackPressed() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Retour"
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            Text(text = nom, color = Color.Black)
            Text(text = adresse,color = Color.DarkGray)
            Text(text = "Note : $note ★")
            var text_favoris="Non"
            if(favoris){
                text_favoris="Oui"
            }
            Text(text = "Favori : ${text_favoris}")

            Text("Horaires :", color = Color.Green)
            horaires.forEach { horaire ->
                Text(
                    text = "${horaire.jour} : ${horaire.horaireMatin} / ${horaire.horaireSoir}",
                    fontSize = 14.sp
                )
            }
        }
    }
}
