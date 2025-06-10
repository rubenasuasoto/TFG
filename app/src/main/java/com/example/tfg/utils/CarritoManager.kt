package com.example.tfg.utils

import android.content.Context
import com.example.tfg.data.models.Producto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

object CarritoManager {
    private const val PREF_NAME = "carrito_pref"
    private const val KEY_CARRITO = "carrito_json"

    fun guardarCarrito(context: Context, productos: List<Producto>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(productos)
        prefs.edit { putString(KEY_CARRITO, json) }
    }

    fun obtenerCarrito(context: Context): List<Producto> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_CARRITO, null)
        return if (json != null) {
            try {
                val type = object : TypeToken<List<Producto>>() {}.type
                Gson().fromJson(json, type)
            } catch (e: Exception) {
                emptyList()
            }
        } else emptyList()
    }

    fun limpiarCarrito(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit { remove(KEY_CARRITO) }
    }
}
