package com.example.fastfood.storage

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.fastfood.model.FastFood
import com.example.fastfood.utility.file.JSONFileStorage
import org.json.JSONObject
import com.example.fastfood.model.Horaire
import com.example.fastfood.model.HoraireJour
import org.json.JSONArray


class FastFoodJSONFileStorage(context: Context) : JSONFileStorage<FastFood>(context, "fastfood") {

    override fun create(id: Int, obj: FastFood): FastFood {
        return FastFood(id,
            obj.nom,
            obj.address,
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
        json.put(FastFood.NOTE, obj.note)
        json.put(FastFood.LATITUDE, obj.latitude)
        json.put(FastFood.LONGITUDE, obj.longitude)
        json.put(FastFood.DESCRIPTION, obj.description)
        json.put(FastFood.FAVORIS, obj.favoris)

        val horairesArray = JSONArray()
        for (horaireJour in obj.horaires) {
            val jourJson = JSONObject()
            jourJson.put("jour", horaireJour.jour)

            val matinJson = JSONObject()
            matinJson.put("ouverture", horaireJour.horaireMatin.ouverture)
            matinJson.put("fermeture", horaireJour.horaireMatin.fermeture)

            val soirJson = JSONObject()
            soirJson.put("ouverture", horaireJour.horaireSoir.ouverture)
            soirJson.put("fermeture", horaireJour.horaireSoir.fermeture)

            jourJson.put("horaireMatin", matinJson)
            jourJson.put("horaireSoir", soirJson)

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
            val horaireMatinJson = jourJson.getJSONObject("horaireMatin")
            val horaireSoirJson = jourJson.getJSONObject("horaireSoir")

            val horaireJour = HoraireJour(
                jourJson.getString("jour"),
                Horaire(horaireMatinJson.getString("ouverture"), horaireMatinJson.getString("fermeture")),
                Horaire(horaireSoirJson.getString("ouverture"), horaireSoirJson.getString("fermeture"))
            )
            horaires.add(horaireJour)
        }

        return FastFood(
            json.getInt(FastFood.ID),
            json.getString(FastFood.NOM),
            json.getString(FastFood.ADDRESS),
            json.getDouble(FastFood.NOTE).toFloat(),
            json.getDouble(FastFood.LATITUDE),
            json.getDouble(FastFood.LONGITUDE),
            json.getString(FastFood.DESCRIPTION),
            json.getBoolean(FastFood.FAVORIS),
            horaires
        )
    }
}

//import com.example.fastfood.storage.utility.file.JSONFileStorage
/*
class FastFoodJSONFileStorage(context: Context) : JSONFileStorage<FastFood>(context,"fastfood")  {
    override fun create(id: Int, obj: FastFood): FastFood {
        return FastFood(id,obj.nom,obj.address,obj.note,obj.latitude,obj.longitude,obj.description,obj.favoris,obj.horaires)
    }
    override fun objectToJson(id: Int, obj: FastFood): JSONObject {
        val json=JSONObject()
        json.put(FastFood.ID,fastfood.id)
        json.put(FastFood.ADDRESS,fastfood.address)
        json.put(FastFood.NOTE,fastfood.note)
        json.put(FastFood.LATITUDE,fastfood.latitude)
        json.put(FastFood.LONGITUDE,fastfood.longitude)
        json.put(FastFood.DESCRIPTION,fastfood.description)
        json.put(FastFood.FAVORIS,fastfood.favoris)
        json.put(FastFood.HORAIREJOUR,fastfood.horaires)
        return json
    }

    override fun jsonToObject(json: JSONObject): FastFood {
        return FastFood(json.getInt(FastFood.ID),
            json.getString(Color.VALUE),
            json.getString(Color.COLOR),
            json.getString(Color.DESCRIPTION))
    }
}
*/