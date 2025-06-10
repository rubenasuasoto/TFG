package com.example.tfg.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun CarritoScreen(
    navController: NavController,
    pedidoViewModel: PedidoViewModel
) {
    val carrito by pedidoViewModel.carrito.collectAsState()
    val total = carrito.sumOf { it.precio ?: 0.0 }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Caja principal - Lista de productos
        Card(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(carrito) { producto ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(producto.articulo ?: "Producto")
                        Text("€${producto.precio}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Caja secundaria - Total y botón
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Total: €${"%.2f".format(total)}", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(
                        onClick = { /* lógica de compra */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Finalizar compra")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
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
