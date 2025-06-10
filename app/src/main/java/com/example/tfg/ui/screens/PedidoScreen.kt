package com.example.tfg.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tfg.ui.components.BottomBarNavigation
import com.example.tfg.ui.navigation.AppScreen
import com.example.tfg.ui.viewModel.PedidoViewModel
import java.util.Date
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PedidosScreen(
    navController: NavHostController,
    pedidoViewModel: PedidoViewModel,
    isUserLoggedIn: Boolean
) {
    val pedidos by pedidoViewModel.pedidos.collectAsState()

    LaunchedEffect(Unit) {
        pedidoViewModel.fetchPedidos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis pedidos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            BottomBarNavigation(
                navController = navController,
                onCarritoClick = {
                    if (isUserLoggedIn) navController.navigate(AppScreen.Carrito.route)
                    else navController.navigate(AppScreen.Login.route)
                },
                onMenuClick = {
                    if (isUserLoggedIn) navController.navigate(AppScreen.Menu.route)
                    else navController.navigate(AppScreen.Login.route)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (pedidos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes pedidos aún.")
                }
                return@Column
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(pedidos) { pedido ->
                    Card(elevation = CardDefaults.cardElevation(4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Numero de pedido: ${pedido.numeroPedido}")
                            Text("Artículo: ${pedido.articulo ?: "N/A"}", style = MaterialTheme.typography.titleMedium)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Estado: ", style = MaterialTheme.typography.bodyMedium)
                                EstadoPedidoLabel(pedido.estado)
                            }

                            Text("Fecha: ${pedido.fechaCreacion.toString().substring(0, 10)}")
                            Text("Total: €${pedido.precioFinal}")

                            if (pedido.estado == "PENDIENTE") {
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        pedido.numeroPedido?.let {
                                            pedidoViewModel.cancelarPedido(it) { ok ->
                                                if (!ok) {
                                                    Log.e("PedidoUI", "❌ No se pudo cancelar")
                                                }
                                            }
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Cancelar pedido")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun EstadoPedidoLabel(estado: String) {
    val color = when (estado.uppercase()) {
        "ENTREGADO" -> MaterialTheme.colorScheme.primary
        "PENDIENTE" -> MaterialTheme.colorScheme.tertiary
        "CANCELADO" -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.outline
    }

    val texto = when (estado.uppercase()) {
        "ENTREGADO" -> "Entregado"
        "PENDIENTE" -> "Pendiente"
        "CANCELADO" -> "Cancelado"
        else -> estado
    }

    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = texto,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

