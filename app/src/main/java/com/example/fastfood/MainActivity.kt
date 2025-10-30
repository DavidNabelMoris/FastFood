package com.example.fastfood

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
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
import androidx.core.content.ContextCompat
import com.example.fastfood.list.FastFoodScreen
import com.example.fastfood.request.FastFoodRequest
import com.example.fastfood.storage.FastFoodStorage
import com.example.fastfood.ui.theme.FastFoodTheme
import com.example.fastfood.model.FastFood
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {

    lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    Log.d("GPS", "Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                } else {
                    Log.d("GPS", "Impossible de récupérer la localisation")
                }
            }
            .addOnFailureListener { e ->
                Log.e("GPS", "Erreur récupération localisation", e)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted){
                setContent {
                    FastFoodTheme {
                        FastFoodScreen()
                    }
                }
            } else {
                Toast.makeText(this, "Permission not granted ...", Toast.LENGTH_SHORT).show()
            }
        }

        // Vérifie si la permission est déjà accordée
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLocation()
            enableEdgeToEdge()
            setContent {
                FastFoodTheme {
                    FastFoodScreen()
                }
            }
        } else {
            setContent {
                FastFoodTheme {
                    Text("Chargement en cours...")
                }
            }
            // Demande la permission
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        //setcontent

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FastFoodTheme {
        FastFoodScreen()
    }
}