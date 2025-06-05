package com.example.tfg.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.Manager.TokenManager
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.io.IOException
import java.util.concurrent.locks.ReentrantLock

// 🔹 Data Models
data class LoginRequest(val username: String, val password: String)

data class Direccion(
    val calle: String,
    val num: String,
    val cp: String,
    val ciudad: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val passwordRepeat: String,
    val rol: String,
    val direccion: Direccion
)
data class Producto(
    val id: String? = null,
    val numeroProducto: String,
    val articulo: String? = null,
    val descripcion: String? = null,
    val precio: Double,
    val stock: Int,
    val imagenUrl: String? = null,
    val fechaCreacion: String? = null,      // ISO 8601 formato desde el backend
    val fechaActualizacion: String? = null
)


data class AuthResponse(val message: String, val token: String?)
data class EstadoUpdateRequest(val estado: String)

// 🔹 Retrofit API Interface
interface ApiService {
    @POST("usuarios/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("usuarios/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse



        // 🔹 Obtener todos los pedidos (solo admin)
        @GET("pedidos")
        suspend fun getAllPedidos(): List<Pedido>

        // 🔹 Obtener solo los pedidos del usuario autenticado
        @GET("pedidos/self")
        suspend fun getUserPedidos(): List<Pedido>

        // 🔹 Obtener un pedido por ID
        @GET("pedidos/{id}")
        suspend fun getPedidoById(@Path("id") id: String): Pedido

        // 🔹 Crear un nuevo pedido (Admin)
        @POST("pedidos")
        suspend fun createPedido(@Body pedido: Pedido): Pedido

        // 🔹 Crear un nuevo pedido propio (Usuarios normales)
        @POST("pedidos/self")
        suspend fun createPedidoSelf(@Body pedido: PedidoDTO): Pedido

        // 🔹 Actualizar el estado de un pedido (Admin)
        @PUT("pedidos/{id}")
        suspend fun updatePedidoEstado(
            @Path("id") id: String,
            @Body pedido: Pedido
        ): Response<Unit>

        // 🔹 Actualizar el estado de un pedido propio (Usuarios normales)
        @PUT("pedidos/self/{id}")
        suspend fun updatePedidoEstadoSelf(
            @Path("id") id: String,
            @Body pedido: Pedido
        ): Response<Unit>

        // 🔹 Eliminar un pedido (Admin)
        @DELETE("pedidos/{id}")
        suspend fun deletePedido(@Path("id") id: String): Response<Unit>

        // 🔹 Eliminar un pedido propio (Usuarios normales)
        @DELETE("pedidos/self/{id}")
        suspend fun deletePedidoSelf(@Path("id") id: String): Response<Unit>
    @GET("productos")
    suspend fun getAllProductos(): List<Producto>

    @GET("productos/numero/{numeroProducto}")
    suspend fun getProductoByNumero(@Path("numeroProducto") numero: String): Producto

    @POST("productos")
    suspend fun crearProducto(@Body producto: Producto): Producto

    @PUT("productos/{id}")
    suspend fun updateProducto(@Path("id") id: String, @Body producto: Producto): Producto

    @DELETE("productos/{id}")
    suspend fun deleteProducto(@Path("id") id: String): Response<Unit>




}

object RetrofitClient {
    private const val BASE_URL = "https://api-rest-segura2.onrender.com"

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


}


// 🔹 ViewModel de Autenticación
class AuthViewModel(
    private val context: Context,
    private val pedidoViewModel: PedidoViewModel
) : ViewModel() {

    fun login(username: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("AuthViewModel", "🔹 Intentando login con usuario: $username")

                // 🔥 Asegurar que no haya un token previo almacenado
                RetrofitClient.updateToken(null, context)
                val response = RetrofitClient.apiService.login(LoginRequest(username, password))
                val token = response.token ?: ""

                Log.d("AuthViewModel", "🔹 Token recibido en login: $token")

                if (token.isNotEmpty()) {
                    RetrofitClient.updateToken(
                        token,
                        context
                    )  // 🔥 Actualizar el token en Retrofit

                    // 🔹 Verificar si el usuario es ADMIN después del login
                    pedidoViewModel.checkAdminStatus(token)

                    onResult(true, "✅ Inicio de sesión exitoso.")
                } else {
                    onResult(false, "❌ Error: Token no recibido.")
                }

            } catch (e: IOException) {
                Log.e("AuthViewModel", "❌ Error de conexión", e)
                onResult(false, "❌ Error de conexión. Verifica tu internet.")
            } catch (e: HttpException) {
                Log.e("AuthViewModel", "❌ Error HTTP: ${e.code()} - ${e.message()}")
                onResult(false, "❌ Error en el servidor: ${e.code()} - ${e.message()}")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "❌ Error desconocido", e)
                onResult(false, "❌ Ocurrió un error inesperado.")
            }
        }
    }




    fun register(
        username: String,
        email: String,
        password: String,
        passwordRepeat: String,
        rol: String,
        direccion: Direccion,
        onResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.register(
                    RegisterRequest(username, email, password, passwordRepeat, rol, direccion)
                )
                onResult("✅ Registro exitoso.")

            } catch (e: HttpException) {
                onResult(
                    if (e.code() == 409) "❌ El usuario o el email ya existen."
                    else "❌ Error en el servidor: ${e.code()} - ${e.message()}"
                )
            } catch (e: IOException) {
                onResult("❌ Error de conexión. Verifica tu internet.")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Error desconocido", e)
                onResult("❌ Ocurrió un error inesperado.")
            }
        }
    }
}