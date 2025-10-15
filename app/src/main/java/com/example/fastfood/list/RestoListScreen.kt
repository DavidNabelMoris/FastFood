package com.example.fastfood.list

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fastfood.model.FastFood
import com.example.fastfood.request.FastFoodRequest
import com.example.fastfood.storage.FastFoodStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FastFoodScreen() {
    val context = LocalContext.current
    var fastFoods by remember { mutableStateOf(listOf<FastFood>()) }
    var isRefreshing by remember { mutableStateOf(false) }

    // Chargement initial
    LaunchedEffect(Unit) {
        isRefreshing = true
        FastFoodRequest(context) {
            fastFoods = FastFoodStorage.get(context).findAll()
            isRefreshing = false
        }
    }

    // Affichage de la liste
    RestoListScreen(
        context = context,
        fastFoods = fastFoods,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            FastFoodRequest(context) {
                fastFoods = FastFoodStorage.get(context).findAll()
                isRefreshing = false
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestoListScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    fastFoods: List<FastFood>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ){

        }
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = isRefreshing,
            onRefresh = onRefresh
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(fastFoods) { food ->
                    Text(
                        text = "${food.nom} - ${food.address}\n" +
                                "Note: ${food.note} ★ | Favori: ${food.favoris}",
                        color = Color.Black
                    )
                    //Text(text = food.horaires.joinToString("\n") { "${it.jour}: ${it.horaireMatin} / ${it.horaireSoir}" })
                }
            }
        }
    }
}
