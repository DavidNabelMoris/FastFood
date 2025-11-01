package com.example.fastfood.list

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.fastfood.R
import com.example.fastfood.model.FastFood
import com.example.fastfood.request.FastFoodRequest
import com.example.fastfood.storage.FastFoodStorage
import com.example.fastfood.ui.theme.FastFoodTheme
import com.example.fastfood.ui.theme.color.ColorPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestoDetailScreen() {
    val context = LocalContext.current
    var fastFood by remember { mutableStateOf<FastFood?>(null) }
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isRefreshing = true
        FastFoodRequest(context) {
            fastFood = FastFoodStorage.get(context).find(id = 1)
            isRefreshing = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestoDetailContent(fastFood: FastFood) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Détail du fast-food",
                        color = FastFoodTheme.colors.textPrimary)
                }
            )
            Text(
                text = "${fastFood.nom}\n - ${fastFood.address}\n" +
                        "Note: ${fastFood.note} ★ | Favori: ${fastFood.favoris}\n" +
                        fastFood.horaires.joinToString("\n") { horaire ->
                            "${horaire.jour}: ${horaire.horaireMatin} / ${horaire.horaireSoir}"
                        }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FastFoodTheme { RestoDetailScreen() }
}

