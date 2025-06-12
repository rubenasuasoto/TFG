package com.example.tfg.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tfg.data.models.Producto
import com.example.tfg.ui.components.BottomBarNavigation

import com.example.tfg.ui.components.ProductoView
import com.example.tfg.ui.navigation.AppScreen
import com.example.tfg.ui.viewModel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoScreen(
    numeroProducto: String,
    navController: NavHostController,
    productoViewModel: ProductoViewModel = viewModel(),
    onAgregarCarrito: (Producto) -> Unit,
    isUserLoggedIn: Boolean
) {
    val productoSeleccionado by productoViewModel.productoSeleccionado.collectAsState()
    val productoError by productoViewModel.productoError.collectAsState()

    LaunchedEffect(numeroProducto) {
        productoViewModel.getProductoByNumero(numeroProducto)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            BottomBarNavigation(
                navController = navController,
                onCarritoClick = {
                    if (isUserLoggedIn) {
                        navController.navigate(AppScreen.Carrito.route)
                    } else {
                        navController.navigate(AppScreen.Login.route)
                    }
                },
                onMenuClick = {
                    if (isUserLoggedIn) {
                        navController.navigate(AppScreen.Menu.route)
                    } else {
                        navController.navigate(AppScreen.Login.route)
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            productoSeleccionado != null -> {
                ProductoView(
                    producto = productoSeleccionado!!,
                    isUserLoggedIn = isUserLoggedIn,
                    onAgregarYIrCarrito = {
                        onAgregarCarrito(productoSeleccionado!!)
                        navController.navigate(AppScreen.Carrito.route)
                    },
                    onLoginRedirect = {
                        navController.navigate(AppScreen.Login.route)
                    }
                )
            }

            productoError -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("❌ Producto no encontrado", color = MaterialTheme.colorScheme.error)
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
