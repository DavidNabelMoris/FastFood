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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import com.example.fastfood.mainpage.RestoMainScreen
import com.example.fastfood.ui.theme.FastFoodTheme
import com.example.fastfood.utility.RestoLocation.getLocation

class MainActivity : ComponentActivity() {

    private var latitude: Double? = null
    private var longitude: Double? = null

    // Permission launcher
    val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val granted = result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                result[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            fetchUserLocation()
            Toast.makeText(this, "Permission accordée", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission refusée", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        // Vérifie si l'une des permissions est déjà accordée
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fetchUserLocation()
        } else {
            // Demande les deux permissions
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

        setContent {
            var refreshTrigger by remember { mutableStateOf(false) }
            FastFoodTheme {
                RestoMainScreen(
                    onUseMyLocation = {
                        requestLocationPermission()
                        refreshTrigger = !refreshTrigger
                    },// <-- matches screen
                    refreshTrigger = refreshTrigger

                )
            }
        }
    }

    /** Vérifie les permissions et récupère la position si accordée **/
    private fun requestLocationPermission() {
        val fineGranted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if(fineGranted||coarseGranted){
            fetchUserLocation()
        }
        else{
            Toast.makeText(this, "Aucune permission", Toast.LENGTH_SHORT).show()
            // Demande les deux permissions
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

    }

    /** Appelle la fonction utilitaire pour récupérer la localisation **/
    private fun fetchUserLocation() {
        getLocation(this) { lat, lon ->
            if (lat != null && lon != null) {
                latitude = lat
                longitude = lon
                Log.d("GPS", "Ma position : $lat, $lon")
                //Toast.makeText(this, "Position : $lat, $lon", Toast.LENGTH_LONG).show()
            } else {
                Log.d("GPS", "Impossible d’obtenir la position")
                Toast.makeText(this, "Impossible d’obtenir la position", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
