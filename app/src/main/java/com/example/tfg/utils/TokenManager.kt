package com.example.tfg.utils
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit

object TokenManager {
    private const val PREF_NAME = "auth_prefs"
    private const val TOKEN_KEY = "auth_token"

    fun saveToken(context: Context, token: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            putString(TOKEN_KEY, token)
            Log.d("TokenManager", "🔐 Token guardado: $token")
        }
    }

    fun getToken(context: Context): String? {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val token = prefs.getString(TOKEN_KEY, null)
        Log.d("TokenManager", "🔎 Token obtenido: $token")
        return token
    }

    fun clearToken(context: Context) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit {
            remove(TOKEN_KEY)
            Log.d("TokenManager", "🚫 Token eliminado de preferencias")
        }
    }
}
