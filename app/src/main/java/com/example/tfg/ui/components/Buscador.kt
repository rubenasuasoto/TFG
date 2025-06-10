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
import androidx.compose.runtime.LaunchedEffect
import com.example.tfg.ui.viewModel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscadorProductosRemoto(
    productoViewModel: ProductoViewModel,
    onProductoSeleccionado: (Producto) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var resultados by remember { mutableStateOf<List<Producto>>(emptyList()) }

    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 2) {
            productoViewModel.buscarProductosPorArticulo(searchQuery) {
                resultados = it
                expanded = it.isNotEmpty()
            }
        } else {
            resultados = emptyList()
            expanded = false
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Buscar producto") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            resultados.forEach { producto ->
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
