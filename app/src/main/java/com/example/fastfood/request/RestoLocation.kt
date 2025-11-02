package com.example.fastfood.utility

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt



object RestoLocation {

    /**
     * Fonction utilitaire pour obtenir la position actuelle de l'utilisateur.
     * Retourne (latitude, longitude) via le callback [onResult].
     */
    @SuppressLint("MissingPermission")
    fun getLocation(context: Context, onResult: (Double?, Double?) -> Unit) {
        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        // Vérifie les permissions avant de continuer
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("GPS", "Permission non accordée")
            onResult(null, null)
            return
        }

        val token = CancellationTokenSource()

        // Essaie d'obtenir la localisation actuelle
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token.token)
            .addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val lon = location.longitude
                    Log.d("GPS", "Latitude: $lat, Longitude: $lon")
                    onResult(lat, lon)
                } else {
                    // Fallback: dernière position connue
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { last ->
                            if (last != null) {
                                val lat = last.latitude
                                val lon = last.longitude
                                Log.d("GPS", "Latitude: $lat, Longitude: $lon (dernière connue)")
                                onResult(lat, lon)
                            } else {
                                Log.d("GPS", "Impossible de récupérer la localisation")
                                onResult(null, null)
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e("GPS", "Erreur récupération localisation", e)
                            onResult(null, null)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("GPS", "Erreur récupération localisation", e)
                onResult(null, null)
            }
    }

    fun calcul_position(context: Context, onResult: (Double?, Double?) -> Unit) {
        getLocation(context) { lat, lon ->
            onResult(lat, lon)
        }
    }


    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371.0
        val d1 = lat1 * PI/180;
        val d2 = lat2 * PI/180;
        val lat = (lat2 - lat1) * PI/180
        val lon = (lon2 - lon1) * PI/180

        val a = sin(lat/2) * sin(lat/2) +
                cos(d1) * cos(d2) *
                sin(lon/2) * sin(lon/2)
        val c = 2 * atan2(sqrt(a),sqrt(1-a))
        val d = r * c  // en KM
        return d;

    }

    }
