package com.example.fastfood.list.composable

import android.util.Log
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.fastfood.model.FastFood
import com.example.fastfood.storage.FastFoodStorage
import com.example.fastfood.storage.FastFoodStorage.estDans
import com.example.fastfood.storage.FastFoodStorage.estDansIndice
import com.example.fastfood.ui.theme.FastFoodTheme
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestoItem(food: FastFood) {
    val colors = FastFoodTheme.colors
    val context = LocalContext.current
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var isFavoris by remember { mutableStateOf(food.favoris) }

    val imgName = remember(food.nom) {
        URLEncoder.encode(food.nom, "UTF-8").replace("+", "%20")
    }
    val imageUrl = "http://51.68.91.213/gr-3-5/img/$imgName.png"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails du restaurant", color = colors.textPrimary) },
                navigationIcon = {
                    IconButton(onClick = { backDispatcher?.onBackPressed() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = colors.textPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.background
                )
            )
        },
        containerColor = colors.background
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .verticalScroll(rememberScrollState())
        ) {
            // ===== Big header image =====
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Photo de ${food.nom}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Bring back the gradient for legibility
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                0f to Color.Transparent,
                                0.55f to Color(0x66000000),
                                1f to Color(0x99000000)
                            )
                        )
                )

                // Title + rating chip
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = food.nom,
                        color = Color.White,                 // stays readable on the image
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    // use primaryContainer / onPrimaryContainer for the chip
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(colors.primaryContainer)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = colors.onPrimaryContainer,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "${food.note} ★",
                                color = colors.onPrimaryContainer,
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                // Floating favorite
                IconButton(
                    onClick = {
                        isFavoris = !isFavoris
                        val storage = FastFoodStorage.get(context)
                        if (isFavoris) {
                            if (!estDans(context, food.nom, food.address)) {
                                storage.insert(food.copy(favoris = true))
                                Log.d("FastFoodUpdate", "Favori ajouté pour ${food.nom}")
                            }
                        } else {
                            estDansIndice(context, food.nom, food.address)?.let { storage.delete(it) }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                ) {
                    Icon(
                        imageVector = if (isFavoris) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favori",
                        tint = colors.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // ===== Info blocks =====
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InfoRow(
                    icon = Icons.Filled.Call,
                    label = "Téléphone",
                    value = food.telephone
                )
                InfoRow(
                    icon = Icons.Filled.Place,
                    label = "Adresse",
                    value = food.address
                )
            }

            // Description (if any)
            if (food.description.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = food.description,
                    color = colors.textPrimary,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            // ===== Horaires =====
            if (food.horaires.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(colors.cardBackground)
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "Horaires",
                            color = colors.textPrimary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        food.horaires.forEach { h ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(h.jour, color = colors.textPrimary)
                                Text("${h.horaireMatin} / ${h.horaireSoir}", color = colors.textPrimary)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    val colors = FastFoodTheme.colors
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colors.cardBackground)        // consistent with your theme
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // small accent icon
            Icon(icon, null, tint = colors.primary, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(12.dp))
            Column {
                Text(label, color = colors.onPrimaryDisabled, fontSize = 12.sp)
                Text(value, color = colors.textPrimary, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}
