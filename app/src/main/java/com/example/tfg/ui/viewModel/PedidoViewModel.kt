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


class PedidoViewModel(application: Application) : AndroidViewModel(application) {

    private val _pedidos = MutableStateFlow<List<Pedido>>(emptyList())
    val pedidos: StateFlow<List<Pedido>> = _pedidos

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin: StateFlow<Boolean> = _isAdmin

    private val _carrito = MutableStateFlow<List<Producto>>(emptyList())
    val carrito: StateFlow<List<Producto>> = _carrito

    fun fetchPedidos() {
        viewModelScope.launch {
            try {
                _pedidos.value = if (_isAdmin.value) {
                    RetrofitClient.apiService.getAllPedidos()
                } else {
                    RetrofitClient.apiService.getUserPedidos()
                }
            } catch (e: Exception) {
                Log.e("PedidoVM", "Error al obtener pedidos", e)
                _pedidos.value = emptyList()
            }
        }
    }

    fun createPedido(pedido: PedidoDTO, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.createPedidoSelf(pedido)
                fetchPedidos()
                limpiarCarrito()
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun updatePedidoEstado(id: String, estado: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val pedidoActual = _pedidos.value.find { it.numeroPedido == id }
                val updated = pedidoActual?.copy(estado = estado)
                updated?.let {
                    val response = RetrofitClient.apiService.updatePedidoEstadoSelf(id, it)
                    onResult(response.isSuccessful)
                } ?: onResult(false)
            } catch (e: Exception) {
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
    fun setAdminStatus(admin: Boolean) {
        _isAdmin.value = admin
    }
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
    fun guardarCarritoLocalmente(lista: List<Producto>) {
        CarritoManager.guardarCarrito(getApplication(), lista)
    }

    fun cargarCarrito() {
        val productos = CarritoManager.obtenerCarrito(getApplication())
        _carrito.value = productos
    }
    fun limpiarCarrito() {
        _carrito.value = emptyList()
        CarritoManager.guardarCarrito(getApplication(), emptyList())
    }



}