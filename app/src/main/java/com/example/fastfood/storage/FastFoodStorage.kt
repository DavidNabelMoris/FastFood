package com.example.fastfood.storage


import android.content.Context
import com.example.fastfood.model.FastFood
import com.example.fastfood.utility.Storage

object FastFoodStorage {
    fun get(context: Context): Storage<FastFood> {
        return FastFoodJSONFileStorage(context)
    }
}
