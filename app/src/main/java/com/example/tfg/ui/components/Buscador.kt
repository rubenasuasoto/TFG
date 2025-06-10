package com.example.tfg.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.tfg.data.models.Producto
import androidx.compose.material3.OutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscadorProductos(
    productos: List<Producto>,
    onProductoSeleccionado: (Producto) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val productosFiltrados = productos.filter {
        it.articulo?.contains(searchQuery, ignoreCase = true) == true
    }

    ExposedDropdownMenuBox(
        expanded = expanded && productosFiltrados.isNotEmpty(),
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                expanded = true
            },
            label = { Text("Buscar artículo") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded && productosFiltrados.isNotEmpty(),
            onDismissRequest = { expanded = false }
        ) {
            productosFiltrados.forEach { producto ->
                DropdownMenuItem(
                    text = { Text(producto.articulo ?: "Sin nombre") },
                    onClick = {
                        onProductoSeleccionado(producto)
                        searchQuery = producto.articulo ?: ""
                        expanded = false
                    }
                )
            }
        }
    }
}
