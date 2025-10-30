package com.example.fastfood.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HoraireJour(
    val jour: String,
    val horaireMatin: String,
    val horaireSoir: String
): Parcelable
@Parcelize
data class FastFood (
    val id: Int,
    val nom: String,
    val address: String,
    val note: Float,
    val latitude: Double,
    val longitude: Double,
    val description: String,
    val favoris: Boolean,
    val horaires: List<HoraireJour>
): Parcelable{
    companion object {
        const val ID = "id"
        const val NOM = "nom"
        const val ADDRESS = "address"
        const val NOTE = "note"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val DESCRIPTION = "description"
        const val FAVORIS = "favoris"
        const val HORAIREJOUR= "horaire"
    }
}
