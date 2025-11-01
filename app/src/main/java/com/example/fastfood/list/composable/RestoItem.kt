package com.example.fastfood.list.composable

import android.content.Context
import android.graphics.Color.green
import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fastfood.model.FastFood
import com.example.fastfood.model.HoraireJour
import com.example.fastfood.storage.FastFoodStorage
import com.example.fastfood.storage.FastFoodStorage.estDans
import com.example.fastfood.storage.FastFoodStorage.estDansIndice
import com.example.fastfood.ui.theme.FastFoodTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestoItem(food: FastFood
) {
    val context = LocalContext.current
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var isFavoris by remember { mutableStateOf(food.favoris) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Détails du restaurant") },
            navigationIcon = {
                IconButton(onClick = { backDispatcher?.onBackPressed() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                    text = food.nom,
                    modifier = Modifier.weight(1f),
                    color = Color.Black,
                    fontSize = 20.sp
                )

                val heartIcon = if (isFavoris) com.example.fastfood.R.drawable.ic_favorite_filled
                else com.example.fastfood.R.drawable.ic_favorite

                IconButton(
                    onClick = {
                        isFavoris = !isFavoris
                        val storage = FastFoodStorage.get(context)

                        if (isFavoris) {
                            // Ajouter dans le fichier local si pas déjà présent
                            if (!estDans(context,food.nom,food.address)) {
                                storage.insert(food.copy(favoris = true))
                                Log.d("FastFoodUpdate", "Favori ajouté pour ${food.nom}")
                            }
                        }

                        else{
                            var id_supprime:Int?=estDansIndice(context,food.nom,food.address)

                            if(id_supprime!=null){
                                storage.delete(id_supprime)
                                Log.d("FastFoodSupprimeFavoris", "Favori supprimer ${food.nom}")
                            }
                            else{
                                Log.d("FastFoodSupprimeFavoris", "Favori introuvable pour ${food.nom}")
                            }
                        }




                    }
                ) {
                    Icon(
                        painter = painterResource(heartIcon),
                        contentDescription = if (isFavoris) "Retirer des favoris" else "Ajouter aux favoris",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                }


                Text(
                    text = "Note : ${food.note} ★",
                    modifier = Modifier.weight(1f),
                    color = Color.Magenta,
                    fontSize = 16.sp
                )
            }

            // Adresse
            Text(
                text = food.address,
                color = Color.DarkGray,
                fontSize = 16.sp
            )

            // Horaires
            Text("Horaires :", color = Color.Green, fontSize = 18.sp)
            food.horaires.forEach { horaire ->
                Text(
                    text = "${horaire.jour} : ${horaire.horaireMatin} / ${horaire.horaireSoir}",
                    fontSize = 14.sp ,
                    modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        } }
}

