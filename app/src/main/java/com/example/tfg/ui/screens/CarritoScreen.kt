package com.example.tfg.ui.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfg.ui.viewModel.PedidoViewModel
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.tfg.ui.components.BottomBarNavigation
import com.example.tfg.ui.navigation.AppScreen

@Composable
fun CarritoScreen(
    navController: NavController,
    pedidoViewModel: PedidoViewModel,
    isUserLoggedIn: Boolean
) {
    val carrito by pedidoViewModel.carrito.collectAsState()
    val total = carrito.sumOf { it.precio ?: 0.0 }

    Scaffold(
        bottomBar = {
            BottomBarNavigation(
                navController = navController,
                onCarritoClick = { /* ya estás aquí */ },
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

            Text("Carrito", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))

            if (carrito.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("🛒 Tu carrito está vacío")
                }
                return@Column
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(carrito) { producto ->
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(modifier = Modifier.weight(1f)) {
                                producto.imagenUrl?.let { url ->
                                    AsyncImage(
                                        model = url,
                                        contentDescription = producto.articulo,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                }

                                Column {
                                    Text(
                                        producto.articulo ?: "Producto",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        "€${producto.precio}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }

                            IconButton(onClick = {
                                pedidoViewModel.eliminarProducto(producto)
                            }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Eliminar del carrito"
                                )
                            }
                        }
                    }
                }
            }


                Spacer(modifier = Modifier.height(16.dp))

                Card(elevation = CardDefaults.cardElevation(4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Total: €${"%.2f".format(total)}",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { /* lógica de compra */ },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Finalizar compra")
                            }
                            OutlinedButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Seguir comprando")
                            }
                        }
                    }
                }
            }
        }
    }
