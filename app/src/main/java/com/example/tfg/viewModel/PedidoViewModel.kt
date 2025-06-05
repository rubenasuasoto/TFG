package com.example.tfg.viewModel
import android.app.Application
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import java.util.Date

data class Pedido(

    val Nºpedido: String?,
    val articulo: String,
    val usuario: String,
    var estado: String,
    val precio: Double,
    val factura: Factura
)

data class PedidoDTO(
    val articulo: String,
    val estado: String,
    val precio: Double,
    val factura: FacturaDTO
)

data class Factura(
    val Nºfactura: String,
    val fecha_compra: Date,
    val forma_de_pago: String
)

data class FacturaDTO(
    val Nºfactura: String,
    val fecha_compra: Date,
    val forma_de_pago: String
)

class PedidoViewModel(application: Application) : AndroidViewModel(application) {
    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin: StateFlow<Boolean> = _isAdmin

    // Función para verificar el rol después del login
    fun checkAdminStatus(token: String) {
        try {
            val jwt = JWT().decodeJwt(token)
            val roles = jwt.getClaim("roles").asList(String::class.java)
            Log.d("PedidoViewModel", "Roles encontrados: $roles")
            _isAdmin.value = roles?.contains("ADMIN") ?: false
            Log.d("PedidoViewModel", "isAdmin actualizado a: ${_isAdmin.value}")
            fetchPedidos()
        } catch (e: Exception) {
            Log.e("PedidoViewModel", "Error al decodificar el token JWT", e)
            _isAdmin.value = false
        }
    }

    fun fetchPedidos() {
        viewModelScope.launch {
            try {
                val response = if (_isAdmin.value) {
                    RetrofitClient.apiService.getAllPedidos()
                } else {
                    RetrofitClient.apiService.getUserPedidos()
                }
                _pedidos.value = response
            } catch (e: Exception) {
                Log.e("PedidoViewModel", "Error al obtener pedidos", e)
                _pedidos.value = emptyList()
            }
        }
    }

    fun getPedidoById(id: String, onResult: (Pedido?) -> Unit) {
        viewModelScope.launch {
            try {
                val pedido = RetrofitClient.apiService.getPedidoById(id)
                withContext(Dispatchers.Main) { onResult(pedido) }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onResult(null) }
            }
        }
    }

    fun createPedido(
        articulo: String,
        estado: String,
        precio: Double,
        factura: FacturaDTO,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = if (_isAdmin.value) {
                    RetrofitClient.apiService.createPedido(
                        Pedido(
                            Nºpedido = null,
                            articulo = articulo,
                            usuario = "", // Se completará en el backend
                            estado = estado,
                            precio = precio,
                            factura = Factura(
                                Nºfactura = factura.Nºfactura,
                                fecha_compra = factura.fecha_compra,
                                forma_de_pago = factura.forma_de_pago
                            )
                        )
                    )
                } else {
                    RetrofitClient.apiService.createPedidoSelf(
                        PedidoDTO(
                            articulo = articulo,
                            estado = estado,
                            precio = precio,
                            factura = factura
                        )
                    )
                }

                Log.d("PedidoViewModel", "✅ Pedido creado: $response")
                fetchPedidos() // Actualizar la lista completa
                onResult(true)
            } catch (e: Exception) {
                Log.e("PedidoViewModel", "❌ Error al crear pedido", e)
                onResult(false)
            }
        }
    }

    fun updatePedidoEstado(
        pedidoId: String,
        nuevoEstado: String,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val pedidoActual = _pedidos.value.find { it.Nºpedido == pedidoId }
                val pedidoActualizado = pedidoActual?.copy(estado = nuevoEstado)

                val response = if (_isAdmin.value) {
                    pedidoActualizado?.let {
                        RetrofitClient.apiService.updatePedidoEstado(pedidoId, it)
                    }
                } else {
                    pedidoActualizado?.let {
                        RetrofitClient.apiService.updatePedidoEstadoSelf(pedidoId, it)
                    }
                }

                if (response?.isSuccessful == true) {
                    Log.d("PedidoViewModel", "✅ Estado del pedido actualizado")
                    fetchPedidos() // Actualizar la lista completa
                    onResult(true)
                } else {
                    Log.e("PedidoViewModel", "❌ Error al actualizar estado")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("PedidoViewModel", "❌ Excepción en updatePedidoEstado", e)
                onResult(false)
            }
        }
    }

    fun deletePedido(pedidoId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = if (_isAdmin.value) {
                    RetrofitClient.apiService.deletePedido(pedidoId)
                } else {
                    RetrofitClient.apiService.deletePedidoSelf(pedidoId)
                }

                if (response.isSuccessful) {
                    Log.d("PedidoViewModel", "✅ Pedido eliminado")
                    fetchPedidos() // Actualizar la lista completa
                    onResult(true)
                } else {
                    Log.e("PedidoViewModel", "❌ Error al eliminar: ${response.code()}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("PedidoViewModel", "❌ Excepción en deletePedido", e)
                onResult(false)
            }
        }
    }
}