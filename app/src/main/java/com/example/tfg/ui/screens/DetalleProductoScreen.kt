package com.example.tfg.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tfg.data.models.Producto
import com.example.tfg.ui.components.ProductoItem
import com.example.tfg.ui.viewModel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    numeroProducto: String,
    navController: NavHostController,
    productoViewModel: ProductoViewModel = viewModel(),
    onAgregarCarrito: (Producto) -> Unit
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
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
            ) { paddingValues ->
                when {
                    productoSeleccionado != null -> {
                        Column(modifier = Modifier.padding(paddingValues)) {
                            ProductoItem(
                                producto = productoSeleccionado!!,
                                onVerDetalle = {}, // Ya estamos aquí
                                onAgregarCarrito = { onAgregarCarrito(productoSeleccionado!!) }
                            )
                        }
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