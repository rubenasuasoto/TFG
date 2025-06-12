package com.example.tfg.ui.screens.Menu

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfg.ui.components.BottomBarNavigation
import com.example.tfg.ui.navigation.AppScreen
import com.example.tfg.ui.viewModel.PedidoViewModel
import com.example.tfg.utils.Strings

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
                title = { Text(Strings.tituloMisPedidos) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = Strings.volver)
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
                    Text(Strings.pedidosVacio)
                }
                return@Column
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(pedidos) { pedido ->
                    var showFactura by remember { mutableStateOf(false) }

                    Card(elevation = CardDefaults.cardElevation(4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("${Strings.pedidoNumero}: ${pedido.numeroPedido}", style = MaterialTheme.typography.titleMedium)

                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()

                            Text(Strings.productos, style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.height(4.dp))

                            pedido.detalles.forEach { producto ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(producto.articulo, style = MaterialTheme.typography.bodyMedium)
                                    Text("€${producto.precio}", style = MaterialTheme.typography.bodyMedium)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(4.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("${Strings.estado}: ", style = MaterialTheme.typography.bodyMedium)
                                EstadoPedidoLabel(pedido.estado)
                            }

                            Text("${Strings.fecha}: ${pedido.fechaCreacion.toString().substring(0, 10)}")
                            Text("${Strings.total}: €${pedido.precioFinal}", style = MaterialTheme.typography.bodyMedium)

                            Spacer(modifier = Modifier.height(8.dp))

                            OutlinedButton(
                                onClick = { showFactura = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Info, contentDescription = Strings.verFactura)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(Strings.verFactura)
                            }

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
                                    Text(Strings.cancelarPedido)
                                }
                            }

                            if (showFactura) {
                                AlertDialog(
                                    onDismissRequest = { showFactura = false },
                                    confirmButton = {
                                        TextButton(onClick = { showFactura = false }) {
                                            Text(Strings.cerrar)
                                        }
                                    },
                                    title = { Text(Strings.factura) },
                                    text = {
                                        Column {
                                            Text("${Strings.numeroFactura}: ${pedido.factura.numeroFactura}")
                                            Text("${Strings.fecha}: ${pedido.factura.fecha.toString().substring(0, 10)}")
                                        }
                                    }
                                )
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
        "ENTREGADO" -> Strings.estadoEntregado
        "PENDIENTE" -> Strings.estadoPendiente
        "CANCELADO" -> Strings.estadoCancelado
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
