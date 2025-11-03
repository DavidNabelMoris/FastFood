package com.example.fastfood.storage

import android.content.Context
import com.example.fastfood.model.FastFood
import com.example.fastfood.storage.utility.file.JSONFileStorage
import org.json.JSONObject
import com.example.fastfood.model.HoraireJour
import org.json.JSONArray


class FastFoodJSONFileStorage(context: Context) : JSONFileStorage<FastFood>(context, "fastfood") {

    override fun create(id: Int, obj: FastFood): FastFood {
        return FastFood(id,
            obj.nom,
            obj.address,
            obj.telephone,
            obj.note,
            obj.latitude,
            obj.longitude,
            obj.description,
            obj.favoris,
            obj.horaires)
    }

    override fun objectToJson(id: Int, obj: FastFood): JSONObject {
        val json = JSONObject()
        json.put(FastFood.ID, id)
        json.put(FastFood.NOM, obj.nom)
        json.put(FastFood.ADDRESS, obj.address)
        json.put(FastFood.TELEPHONE, obj.telephone)
        json.put(FastFood.NOTE, obj.note)
        json.put(FastFood.LATITUDE, obj.latitude)
        json.put(FastFood.LONGITUDE, obj.longitude)
        json.put(FastFood.DESCRIPTION, obj.description)
        json.put(FastFood.FAVORIS, obj.favoris)

        val horairesArray = JSONArray()
        for (horaireJour in obj.horaires) {
            val jourJson = JSONObject()
            jourJson.put("jour", horaireJour.jour)

            jourJson.put("horaireMatin", horaireJour.horaireMatin)
            jourJson.put("horaireSoir", horaireJour.horaireSoir)

            horairesArray.put(jourJson)
        }
        json.put(FastFood.HORAIREJOUR, horairesArray)

        return json
    }

    override fun jsonToObject(json: JSONObject): FastFood {
        val horaires = mutableListOf<HoraireJour>()
        val horairesArray = json.getJSONArray(FastFood.HORAIREJOUR)
        for (i in 0 until horairesArray.length()) {
            val jourJson = horairesArray.getJSONObject(i)

            val horaireMatin = jourJson.getString("horaireMatin")
            val horaireSoir = jourJson.getString("horaireSoir")

            val horaireJour = HoraireJour(
                jourJson.getString("jour"),
                horaireMatin,
                horaireSoir
            )
            //Log.d("DEBUG", "$jourJson")
            //Log.d("DEBUG", "horaireMatin = $horaireMatin")
            horaires.add(horaireJour)
        }


        return FastFood(
            json.getInt(FastFood.ID),
            json.getString(FastFood.NOM),
            json.getString(FastFood.ADDRESS),
            json.getString(FastFood.TELEPHONE),
            json.getDouble(FastFood.NOTE).toFloat(),
            json.getDouble(FastFood.LATITUDE),
            json.getDouble(FastFood.LONGITUDE),
            json.getString(FastFood.DESCRIPTION),
            json.getBoolean(FastFood.FAVORIS),
            horaires
        )
    }
}