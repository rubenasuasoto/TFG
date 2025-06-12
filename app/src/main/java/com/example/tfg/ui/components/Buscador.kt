package com.example.tfg.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
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
import com.example.tfg.utils.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscadorProductosRemoto(
    productoViewModel: ProductoViewModel,
    onProductoSeleccionado: (Producto) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var resultados by remember { mutableStateOf<List<Producto>>(emptyList()) }
    var hasSearched by remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 2) {
            hasSearched = true
            productoViewModel.buscarProductosPorArticulo(searchQuery) {
                resultados = it
                expanded = it.isNotEmpty()
            }
        } else {
            resultados = emptyList()
            expanded = false
            hasSearched = false
        }
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(Strings.buscarProductoLabel) },
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
                    text = { Text(producto.articulo ?: Strings.sinNombre) },
                    onClick = {
                        onProductoSeleccionado(producto)
                        searchQuery = producto.articulo ?: ""
                        expanded = false
                    }
                )
            }

            if (hasSearched && resultados.isEmpty()) {
                DropdownMenuItem(
                    text = {
                        Text(
                            Strings.noResultados,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    onClick = {},
                    enabled = false
                )
            }
        }
    }
}
