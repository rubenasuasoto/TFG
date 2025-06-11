package com.example.tfg.ui.viewModel
import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.tfg.data.models.Pedido
import com.example.tfg.data.models.PedidoDTO
import com.example.tfg.data.models.Producto
import com.example.tfg.data.remote.RetrofitClient
import com.example.tfg.utils.CarritoManager
import kotlinx.coroutines.flow.MutableStateFlow

data class EstadoDTO(
    val estado: String
)

class PedidoViewModel(application: Application) : AndroidViewModel(application) {

    // ░░░ State ░░░
    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos

    private val _pedidosAdmin = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidosAdmin: StateFlow<List<Pedido>> = _pedidosAdmin

    private val _carrito = MutableStateFlow<List<Producto>>(emptyList())
    val carrito: StateFlow<List<Producto>> = _carrito

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin: StateFlow<Boolean> = _isAdmin

    // ░░░ Pedidos usuario ░░░
    fun fetchPedidos() {
        viewModelScope.launch {
            try {
                val resultado = RetrofitClient.apiService.getUserPedidos()
                _pedidos.value = resultado
            } catch (e: Exception) {
                Log.e("PedidoVM", "❌ Error al obtener pedidos del usuario", e)
                _pedidos.value = emptyList()
            }
        }
    }

    fun createPedido(pedido: PedidoDTO, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.createPedidoSelf(pedido)
                fetchPedidos()
                limpiarCarrito()
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun cancelarPedido(numeroPedido: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deletePedidoSelf(numeroPedido)
                if (response.isSuccessful) {
                    fetchPedidos()
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun finalizarCompra(onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val carritoActual = carrito.value
                if (carritoActual.isEmpty()) {
                    onResult(false, "❌ El carrito está vacío")
                    return@launch
                }

                val ids = carritoActual.map { it.numeroProducto }
                val total = carritoActual.sumOf { it.precio }

                val dto = PedidoDTO(productos = ids, precioFinal = total)

                RetrofitClient.apiService.createPedidoSelf(dto)

                limpiarCarrito()
                fetchPedidos()

                onResult(true, "✅ Pedido realizado correctamente")
            } catch (e: Exception) {
                Log.e("PedidoVM", "❌ Error al crear pedido", e)
                onResult(false, "❌ Error al finalizar la compra")
            }
        }
    }

    // ░░░ Pedidos admin ░░░
    fun fetchAllPedidosAdmin() {
        viewModelScope.launch {
            try {
                val resultado = RetrofitClient.apiService.getAllPedidos()
                _pedidosAdmin.value = resultado
            } catch (e: Exception) {
                Log.e("PedidoVM", "❌ Error al obtener pedidos admin", e)
            }
        }
    }

    fun cambiarEstadoPedido(numeroPedido: String, nuevoEstado: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.updatePedidoEstado(numeroPedido, EstadoDTO(nuevoEstado))
                if (response.isSuccessful) {
                    fetchAllPedidosAdmin()
                } else {
                    Log.e("PedidoVM", "❌ No se pudo cambiar estado (${response.code()})")
                }
            } catch (e: Exception) {
                Log.e("PedidoVM", "❌ Error al cambiar estado", e)
            }
        }
    }

    fun eliminarPedidoAdmin(numeroPedido: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deletePedido(numeroPedido)
                if (response.isSuccessful) {
                    fetchAllPedidosAdmin()
                } else {
                    Log.e("PedidoVM", "❌ No se pudo eliminar pedido (${response.code()})")
                }
            } catch (e: Exception) {
                Log.e("PedidoVM", "❌ Error al eliminar pedido", e)
            }
        }
    }

    // ░░░ Carrito ░░░
    fun agregarProducto(producto: Producto) {
        val actualizado = _carrito.value.toMutableList().apply { add(producto) }
        _carrito.value = actualizado
        guardarCarritoLocalmente(actualizado)
    }

    fun eliminarProducto(producto: Producto) {
        val actualizado = _carrito.value.toMutableList().apply { remove(producto) }
        _carrito.value = actualizado
        guardarCarritoLocalmente(actualizado)
    }

    fun limpiarCarrito() {
        _carrito.value = emptyList()
        guardarCarritoLocalmente(emptyList())
    }

    fun cargarCarrito() {
        val productos = CarritoManager.obtenerCarrito(getApplication())
        _carrito.value = productos
    }

    fun guardarCarritoLocalmente(lista: List<Producto>) {
        CarritoManager.guardarCarrito(getApplication(), lista)
    }

    // ░░░ Admin status ░░░
    fun setAdminStatus(admin: Boolean) {
        _isAdmin.value = admin
    }
}
