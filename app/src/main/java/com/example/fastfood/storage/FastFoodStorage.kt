package com.example.fastfood.storage


import android.content.Context
import com.example.fastfood.model.FastFood
import com.example.fastfood.utility.Storage

object FastFoodStorage {
    fun get(context: Context): Storage<FastFood> {
        return FastFoodJSONFileStorage(context)
    }
    fun estDans(context: Context,nom: String,adress: String): Boolean {
        for (fastFood in FastFoodStorage.get(context).findAll()) {
            if(fastFood.nom==nom && fastFood.address==adress){
                return true
            }
        }
        return false
    }
    fun estDansIndice(context: Context,nom: String,adress: String): Int? {
        for (fastFood in FastFoodStorage.get(context).findAll()) {
            if(fastFood.nom==nom && fastFood.address==adress){
                return fastFood.id
            }
        }
        return null
    }
}
