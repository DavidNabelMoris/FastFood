package com.example.fastfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.fastfood.list.FastFoodScreen
import com.example.fastfood.request.FastFoodRequest
import com.example.fastfood.storage.FastFoodStorage
import com.example.fastfood.model.FastFood
import com.example.fastfood.ui.theme.FastFoodTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FastFoodTheme {
                FastFoodScreen()
            }
        }
    }
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FastFoodScreen() {
    val context = LocalContext.current
    var fastFoods by remember { mutableStateOf(listOf<FastFood>()) }
    var isRefreshing by remember { mutableStateOf(false) }
    // LaunchedEffect = se lance automatiquement au démarrage
    LaunchedEffect(Unit) {
        isRefreshing = true
        FastFoodRequest(context) {
            fastFoods = FastFoodStorage.get(context).findAll()
            isRefreshing = false
        }
    }
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            FastFoodRequest(context) {
                fastFoods = FastFoodStorage.get(context).findAll()
                isRefreshing = false
            }
        }
    ) {
        LazyColumn {
            items(fastFoods) { food ->
                Text("${food.nom} - ${food.address} - Note: ${food.note} - Favori: ${food.favoris}")
                Text("${food.horaires}")
            }
        }
    }
}*/



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FastFoodTheme {
        FastFoodScreen()
    }
}