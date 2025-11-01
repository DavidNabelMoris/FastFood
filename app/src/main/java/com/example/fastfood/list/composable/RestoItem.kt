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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastfood.list.FastFoodScreen
import com.example.fastfood.model.HoraireJour
import com.example.fastfood.ui.theme.FastFoodTheme

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
    var isFavoris by remember { mutableStateOf(favoris) }
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Ligne nom, favoris, note
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = nom,
                    modifier = Modifier.weight(1f),
                    color = Color.Black,
                    fontSize = 20.sp
                )

                val heartIcon = if (isFavoris) com.example.fastfood.R.drawable.ic_favorite_filled
                else com.example.fastfood.R.drawable.ic_favorite

                IconButton(
                    onClick = { isFavoris = !isFavoris }
                ) {
                    Icon(
                        painter = painterResource(heartIcon),
                        contentDescription = if (isFavoris) "Retirer des favoris" else "Ajouter aux favoris",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                }


                Text(
                    text = "Note : $note ★",
                    modifier = Modifier.weight(1f),
                    color = Color.Magenta,
                    fontSize = 16.sp
                )
            }

            // Adresse
            Text(
                text = adresse,
                color = Color.DarkGray,
                fontSize = 16.sp
            )

            // Horaires
            Text("Horaires :", color = Color.Green, fontSize = 18.sp)
            horaires.forEach { horaire ->
                Text(
                    text = "${horaire.jour} : ${horaire.horaireMatin} / ${horaire.horaireSoir}",
                    fontSize = 14.sp ,
                    modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
    } }
}


@Preview(showBackground = true)
@Composable
fun RestoItemPreview() {
    FastFoodTheme {
        RestoItem(
            nom = "Burger Street",
            adresse = "12 Rue de la République, Chambéry",
            note = 4.5f,
            favoris = true,
            horaires = listOf(
                HoraireJour("Lundi", "11:00–14:00", "17:30–21:00"),
                HoraireJour("Mardi", "11:00–14:00", "17:30–21:00"),
                HoraireJour("Mercredi", "11:00–14:00", "17:30–22:00")
            )
        )
    }
}
