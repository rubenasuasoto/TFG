package com.example.tfg.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tfg.data.models.Producto
import com.example.tfg.ui.components.BottomBarNavigation

import com.example.tfg.ui.components.BuscadorProductosRemoto
import com.example.tfg.ui.components.ProductoCardCarrusel
import com.example.tfg.ui.components.ProductoCardGrid
import com.example.tfg.ui.navigation.AppScreen
import com.example.tfg.ui.viewModel.ProductoViewModel


@Composable
fun HomeScreen(
    navController: NavHostController,
    productoViewModel: ProductoViewModel = viewModel(),
    onAddToCart: (Producto) -> Unit,
    isUserLoggedIn: Boolean
) {
    val productos by productoViewModel.productos.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showLoginPrompt by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        productoViewModel.fetchAllProductos()
    }

    val recomendadosIds = listOf("P001", "P002", "P003")
    val recomendados = productos.filter { it.numeroProducto in recomendadosIds }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomBarNavigation(
                navController = navController,
                onCarritoClick = {
                    if (isUserLoggedIn) {
                        navController.navigate(AppScreen.Carrito.route)
                    } else {
                        showLoginPrompt = true
                    }
                },
                onMenuClick = {
                    if (isUserLoggedIn) {
                        navController.navigate(AppScreen.Menu.route)
                    } else {
                        showLoginPrompt = true
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)
            .fillMaxSize()
        ) {

            BuscadorProductosRemoto(productoViewModel) { productoSeleccionado ->
                navController.navigate("detalle_producto/${productoSeleccionado.numeroProducto}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Primero sección de Recomendados
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

            Spacer(modifier = Modifier.height(16.dp))

            // Luego sección de Productos
            Text("Productos", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
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
            if (showLoginPrompt) {
                LaunchedEffect(Unit) {
                    val result = snackbarHostState.showSnackbar(
                        message = "Necesitas iniciar sesión",
                        actionLabel = "Iniciar sesión",
                        withDismissAction = true
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        navController.navigate(AppScreen.Login.route)
                    }
                    showLoginPrompt = false
                }
            }
        }
    }
}