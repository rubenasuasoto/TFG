package com.example.tfg.data.remote

import android.content.Context
import android.util.Log
import com.example.tfg.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.locks.ReentrantLock

object RetrofitClient {
    private const val BASE_URL = "https://api-tfg-64f8.onrender.com"

    @Volatile
    private var token: String? = null
    private val lock = ReentrantLock()

    // 🔹 Interceptor para agregar el token a las solicitudes
    private val authInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        val currentToken: String?

        lock.lock()
        try {
            currentToken = token
        } finally {
            lock.unlock()
        }

        if (!currentToken.isNullOrEmpty()) { // ✅ Solo agregar si hay token
            Log.d("RetrofitClient", "🔹 Enviando token: Bearer $currentToken")
            requestBuilder.addHeader("Authorization", "Bearer $currentToken")
        } else {
            Log.d("RetrofitClient", "⚠️ No se envía token porque es null o vacío")
        }

        chain.proceed(requestBuilder.build())
    }



    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

    // 🔹 Método para actualizar el token después del login
    fun updateToken(newToken: String?, context: Context) {
        Log.d("RetrofitClient", "🔹 updateToken() ha sido llamado con token: $newToken")
        lock.lock()
        try {
            token = newToken
            Log.d("RetrofitClient", "🔹 Nuevo Token guardado: $token")  // Este ya está
            newToken?.let {
                TokenManager.saveToken(context, it)
            }
        } finally {
            lock.unlock()
        }
    }
    fun configureTokenFromStorage(context: Context) {
        val savedToken = TokenManager.getToken(context)
        if (!savedToken.isNullOrEmpty()) {
            Log.d("RetrofitClient", "✅ Token cargado desde SharedPreferences")
            updateToken(savedToken, context)
        } else {
            Log.d("RetrofitClient", "⚠️ No se encontró token persistido")
        }
    }



}