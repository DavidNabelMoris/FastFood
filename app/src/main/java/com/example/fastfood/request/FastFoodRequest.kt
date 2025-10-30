package com.example.fastfood.request

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.fastfood.R
import com.example.fastfood.model.FastFood
import com.example.fastfood.model.HoraireJour
import com.example.fastfood.storage.FastFoodStorage
import org.json.JSONObject

//recuperation des FastFood depuis une API
class FastFoodRequest(private val context: Context, onUpdate: (List<FastFood>) -> Unit) {
    // Variable qui contient tous les restaurants
    val allFastFoods = mutableListOf<FastFood>()
    init {
        val queue = Volley.newRequestQueue(context)

        val request = JsonObjectRequest(
            Request.Method.GET,
            "http://51.68.91.213/gr-3-5/Resto.json",
            null,
            { res ->
                Log.e("FastFoodRequest", "Ces bien JSON :")
                Toast.makeText(context, R.string.success, Toast.LENGTH_SHORT).show()
                refresh(res)
                onUpdate(allFastFoods) // passe la liste
            },
            { err ->
                Log.e("FastFoodRequest", "Erreur téléchargement JSON : ${err.message}", err)
                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)
    }

    private fun refresh(json: JSONObject) {
        delete()
        insert(json)
    }

    private fun delete() {
        for (fastFood in FastFoodStorage.get(context).findAll()) {
            FastFoodStorage.get(context).delete(fastFood.id)
        }
    }


    private fun insert(json: JSONObject) {
        val fastfoods = json.getJSONArray("fastfoods")

        for (i in 0 until fastfoods.length()) {
            val obj = fastfoods.getJSONObject(i)

            // Conversion du tableau d’horaires JSON en liste
            val horairesArray = obj.getJSONArray(FastFood.HORAIREJOUR)
            val horaires = mutableListOf<HoraireJour>()

            for (i in 0 until horairesArray.length()) {
                val jourJson = horairesArray.getJSONObject(i)
                val matin = jourJson.getString("horaireMatin")
                val soir = jourJson.getString("horaireSoir")

                val horaireJour = HoraireJour(
                    jourJson.getString("jour"),
                    matin,
                    soir
                )
                horaires.add(horaireJour)
            }

            //Création de l’objet FastFood
            val fastfood: FastFood=FastFood(id = 0,
            obj.getString(FastFood.NOM),
            obj.getString(FastFood.ADDRESS),
            obj.getDouble(FastFood.NOTE).toFloat(),
            obj.getDouble(FastFood.LATITUDE),
            obj.getDouble(FastFood.LONGITUDE),
            obj.getString(FastFood.DESCRIPTION),
            obj.getBoolean(FastFood.FAVORIS),
            horaires)

            // insertion de l’objet FastFood dans le fichier local si favoris
            if(fastfood.favoris){
                FastFoodStorage.get(context).insert(fastfood)
            }

            // Ajoute tous les restaurants dans la variable mémoire
            allFastFoods.add(fastfood)
        }
    }
}
