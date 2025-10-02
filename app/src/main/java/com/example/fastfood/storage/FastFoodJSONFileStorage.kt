package com.example.fastfood.storage

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.example.fastfood.model.FastFood
import org.json.JSONObject
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