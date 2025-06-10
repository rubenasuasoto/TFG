package com.example.tfg.data.remote

import com.example.tfg.data.models.AuthResponse
import com.example.tfg.data.models.LoginRequest
import com.example.tfg.data.models.Pedido
import com.example.tfg.data.models.PedidoDTO
import com.example.tfg.data.models.Producto
import com.example.tfg.data.models.Usuario
import com.example.tfg.data.models.UsuarioDTO
import com.example.tfg.data.models.UsuarioUpdateDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


// 🔹 Retrofit API Interface
interface ApiService {
    // Autenticación
    @POST("usuarios/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("usuarios/register")
    suspend fun register(@Body request: Usuario): AuthResponse

    // Usuarios
    @GET("usuarios/self")
    suspend fun getSelf(): UsuarioDTO

    @PUT("usuarios/self")
    suspend fun updateSelf(@Body dto: UsuarioUpdateDTO): Response<Unit>

    @GET("usuarios/check-admin")
    suspend fun checkAdmin(): Response<Map<String, Boolean>>

    @POST("pedidos/self")
    suspend fun createPedidoSelf(@Body pedido: PedidoDTO): Pedido

    @PUT("pedidos/self/{id}")
    suspend fun updatePedidoEstadoSelf(
        @Path("id") id: String,
        @Body pedido: Pedido
    ): Response<Unit>


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


    // 🔹 Actualizar el estado de un pedido (Admin)
    @PUT("pedidos/{id}")
    suspend fun updatePedidoEstado(
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

    @GET("productos/{numeroProducto}")
    suspend fun getProductoByNumero(@Path("numeroProducto") numero: String): Producto

    @POST("productos")
    suspend fun crearProducto(@Body producto: Producto): Producto

    @PUT("productos/{id}")
    suspend fun updateProducto(@Path("id") id: String, @Body producto: Producto): Producto

    @DELETE("productos/{id}")
    suspend fun deleteProducto(@Path("id") id: String): Response<Unit>




}