package com.example.tfg.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.tfg.ui.components.DropdownMenuCambioEstado
import com.example.tfg.ui.screens.Menu.EstadoPedidoLabel
import com.example.tfg.ui.viewModel.PedidoViewModel
import com.example.tfg.utils.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPedidosScreen(
    navController: NavHostController,
    pedidoViewModel: PedidoViewModel
) {
    val pedidos by pedidoViewModel.pedidosAdmin.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        pedidoViewModel.fetchAllPedidosAdmin()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.adminPedidosTitulo) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = Strings.volver)
                    }
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
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text(Strings.adminPedidosBuscarLabel) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            val pedidosFiltrados = pedidos.filter { pedido ->
                val query = searchQuery.trim().lowercase()
                val usuario = pedido.usuario?.lowercase() ?: ""
                val estado = pedido.estado.lowercase()
                val productos = pedido.detalles.joinToString(" ") { it.articulo.lowercase() }
                query in usuario || query in estado || query in productos
            }

            if (pedidosFiltrados.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(Strings.adminPedidosNoResultados)
                }
                return@Column
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(pedidosFiltrados) { pedido ->
                    Card(elevation = cardElevation(4.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("${Strings.pedidoNumero}: ${pedido.numeroPedido}", style = MaterialTheme.typography.titleMedium)
                            Text("${Strings.usuario}: ${pedido.usuario}", style = MaterialTheme.typography.bodyMedium)

                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(4.dp))

                            Text(Strings.productos, style = MaterialTheme.typography.titleSmall)
                            pedido.detalles.forEach {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(it.articulo)
                                    Text("€${"%.2f".format(it.precio)}")
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("${Strings.estado}: ", style = MaterialTheme.typography.bodyMedium)
                                EstadoPedidoLabel(pedido.estado)
                            }

                            Text("${Strings.total}: €${"%.2f".format(pedido.precioFinal)}")
                            Text("${Strings.fecha}: ${pedido.fechaCreacion.toString().substring(0, 10)}")

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                DropdownMenuCambioEstado(
                                    pedido.numeroPedido ?: "",
                                    pedido.estado,
                                    pedidoViewModel
                                )
                                OutlinedButton(
                                    onClick = {
                                        pedido.numeroPedido?.let {
                                            pedidoViewModel.eliminarPedidoAdmin(it)
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = Strings.eliminar)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(Strings.eliminar)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
