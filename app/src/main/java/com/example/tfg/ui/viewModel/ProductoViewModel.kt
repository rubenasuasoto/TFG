package com.example.tfg.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.data.models.Producto
import com.example.tfg.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(application: Application) : AndroidViewModel(application) {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _productoSeleccionado = MutableStateFlow<Producto?>(null)
    val productoSeleccionado: StateFlow<Producto?> = _productoSeleccionado

    private val _productoError = MutableStateFlow(false)
    val productoError: StateFlow<Boolean> = _productoError

    fun fetchAllProductos() {
        viewModelScope.launch {
            try {
                val productos = RetrofitClient.apiService.getAllProductos()
                _productos.value = productos
            } catch (e: Exception) {
                Log.e("ProductoViewModel", "❌ Error al obtener productos", e)
                _productos.value = emptyList()
            }
        }
    }

    fun getProductoByNumero(numero: String, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                val producto = RetrofitClient.apiService.getProductoByNumero(numero)
                _productoSeleccionado.value = producto
                _productoError.value = false
            } catch (e: Exception) {
                Log.e("ProductoVM", "❌ Error al obtener producto", e)
                _productoSeleccionado.value = null
                _productoError.value = true
            }
            onComplete()
        }
    }

    fun crearProducto(producto: Producto, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.crearProducto(producto)
                fetchAllProductos()
                onResult(true)
            } catch (e: Exception) {
                Log.e("ProductoViewModel", "❌ Error al crear producto", e)
                onResult(false)
            }
        }
    }

    fun updateProducto(id: String, producto: Producto, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.updateProducto(id, producto)
                fetchAllProductos()
                onResult(true)
            } catch (e: Exception) {
                Log.e("ProductoViewModel", "❌ Error al actualizar producto", e)
                onResult(false)
            }
        }
    }

    fun deleteProducto(id: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deleteProducto(id)
                if (response.isSuccessful) {
                    fetchAllProductos()
                    onResult(true)
                } else {
                    Log.e("ProductoViewModel", "❌ Error al eliminar producto")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("ProductoViewModel", "❌ Excepción al eliminar producto", e)
                onResult(false)
            }
        }
    }
    fun buscarProductosPorArticulo(query: String, onResult: (List<Producto>) -> Unit) {
        viewModelScope.launch {
            try {
                val resultado = RetrofitClient.apiService.buscarProductosPorNombre(query)
                onResult(resultado)
            } catch (e: Exception) {
                Log.e("ProductoVM", "❌ Error en búsqueda parcial", e)
                onResult(emptyList())
            }
        }
    }


}
