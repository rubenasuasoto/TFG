package com.example.tfg.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tfg.data.models.Producto
import com.example.tfg.ui.components.BuscadorProductos
import com.example.tfg.ui.components.ProductoCardCarrusel
import com.example.tfg.ui.components.ProductoCardGrid
import com.example.tfg.ui.viewModel.ProductoViewModel


@Composable
fun HomeScreen(
    navController: NavHostController,
    productoViewModel: ProductoViewModel = viewModel(),
    onAddToCart: (Producto) -> Unit,
    isUserLoggedIn: Boolean
) {
    val productos by productoViewModel.productos.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        productoViewModel.fetchAllProductos()
    }

    

    // Productos recomendados seleccionados por su numeroProducto
    val recomendadosIds = listOf("P001", "P002", "P003") // tú defines estos códigos
    val recomendados = productos.filter { it.numeroProducto in recomendadosIds }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        BuscadorProductos(productos = productos) { productoSeleccionado ->
            navController.navigate("detalle_producto/${productoSeleccionado.numeroProducto}")
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text("Productos", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxHeight(0.6f)
        ) {
            items(productos) { producto ->
                ProductoCardGrid(
                    producto = producto,
                    onVerDetalle = { navController.navigate("detalle_producto/${producto.numeroProducto}") },
                    onAgregarCarrito = {
                        if (isUserLoggedIn) onAddToCart(producto)
                        else navController.navigate("login")
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Recomendados", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(recomendados) { producto ->
                ProductoCardCarrusel(
                    producto = producto,
                    onVerDetalle = { navController.navigate("detalle_producto/${producto.numeroProducto}") }
                )
            }
        }
    }
}
