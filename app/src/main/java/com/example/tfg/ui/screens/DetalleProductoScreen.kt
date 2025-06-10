package com.example.tfg.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.tfg.data.models.Producto
import com.example.tfg.ui.viewModel.ProductoViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.tfg.ui.components.ProductoItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    numeroProducto: String,
    navController: NavHostController,
    productoViewModel: ProductoViewModel = viewModel(),
    onAgregarCarrito: (Producto) -> Unit
) {
    val productoSeleccionado by productoViewModel.productoSeleccionado.collectAsState()

    LaunchedEffect(numeroProducto) {
        productoViewModel.getProductoByNumero(numeroProducto) {}
    }

    productoSeleccionado?.let { producto ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(producto.articulo ?: "Detalle") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                ProductoItem(
                    producto = producto,
                    onVerDetalle = {}, // Ya estamos en el detalle
                    onAgregarCarrito = { onAgregarCarrito(producto) }
                )
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
