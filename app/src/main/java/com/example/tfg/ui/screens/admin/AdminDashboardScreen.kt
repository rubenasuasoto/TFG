package com.example.tfg.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.tfg.ui.viewModel.AuthViewModel
import com.example.tfg.ui.viewModel.PedidoViewModel
import com.example.tfg.ui.viewModel.ProductoViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.example.tfg.ui.components.DashboardCard
import com.example.tfg.utils.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    productoViewModel: ProductoViewModel,
    pedidoViewModel: PedidoViewModel,
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val productos by productoViewModel.productos.collectAsState()
    val pedidos by pedidoViewModel.pedidosAdmin.collectAsState()
    val usuarios by authViewModel.listaUsuarios.collectAsState()

    val totalProductos = productos.size
    val totalPedidos = pedidos.size
    val pedidosPendientes = pedidos.count { it.estado == "PENDIENTE" }
    val pedidosCompletados = pedidos.count { it.estado == "COMPLETADO" }
    val ventasTotales = pedidos.filter { it.estado == "COMPLETADO" }.sumOf { it.precioFinal }
    val totalUsuarios = usuarios.size

    LaunchedEffect(Unit) {
        productoViewModel.fetchAllProductos()
        pedidoViewModel.fetchAllPedidosAdmin()
        authViewModel.fetchAllUsuarios()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.adminDashboardTitulo) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = Strings.volver)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DashboardCard(title = Strings.adminDashboardProductos, value = totalProductos.toString())
            DashboardCard(title = Strings.adminDashboardUsuarios, value = totalUsuarios.toString())
            DashboardCard(title = Strings.adminDashboardPedidosTotales, value = totalPedidos.toString())
            DashboardCard(title = Strings.adminDashboardPedidosPendientes, value = pedidosPendientes.toString())
            DashboardCard(title = Strings.adminDashboardPedidosCompletados, value = pedidosCompletados.toString())
            DashboardCard(
                title = Strings.adminDashboardVentasTotales,
                value = "€${"%.2f".format(ventasTotales)}"
            )
        }
    }
}
