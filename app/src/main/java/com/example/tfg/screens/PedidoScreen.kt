package com.example.tfg.screens

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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.tfg.viewModel.FacturaDTO
import com.example.tfg.viewModel.PedidoViewModel
import java.util.Date

@Composable
fun PedidoScreen(navController: NavController, viewModel: PedidoViewModel) {
    val pedidos by viewModel.pedidos.collectAsState()
    val isAdmin by viewModel.isAdmin.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPedidos()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "Lista de Pedidos",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(pedidos) { pedido ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🛒 Nº Pedido: ${pedido.Nºpedido}", fontWeight = FontWeight.Bold)
                        Text("📦 Artículo: ${pedido.articulo}")
                        Text("💰 Precio: ${pedido.precio}€")
                        Text("✅ Estado: ${pedido.estado}")
                        Text("🧾 Factura: ${pedido.factura.Nºfactura}")
                        Text("📅 Fecha: ${pedido.factura.fecha_compra}")
                        Text("💳 Pago: ${pedido.factura.forma_de_pago}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Button(
                                onClick = { navController.navigate("pedido_form/${pedido.Nºpedido}") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("✏️ Editar")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = { pedido.Nºpedido?.let { viewModel.deletePedido(it) { /* Manejar resultado */ } } },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("🗑️ Eliminar")
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("pedido_form/new") }) {
            Text("➕ Crear Nuevo Pedido")
        }
    }
}

@Composable
fun PedidoFormScreen(navController: NavController, viewModel: PedidoViewModel, pedidoId: String?) {
    val articulo = remember { mutableStateOf("") }
    val precio = remember { mutableStateOf("") }
    val estado = remember { mutableStateOf("PENDIENTE") }
    val nFactura = remember { mutableStateOf("") }
    val formaPago = remember { mutableStateOf("TARJETA") }
    val isLoading = remember { mutableStateOf(false) }
    val isEditing = remember { mutableStateOf(pedidoId != "new") }
    val errorMessage = remember { mutableStateOf("") }
    val estadoExpanded = remember { mutableStateOf(false) }
    val pagoExpanded = remember { mutableStateOf(false) }

    LaunchedEffect(pedidoId) {
        if (isEditing.value) {
            viewModel.getPedidoById(pedidoId!!) { pedido ->
                if (pedido != null) {
                    articulo.value = pedido.articulo
                    precio.value = pedido.precio.toString()
                    estado.value = pedido.estado
                    nFactura.value = pedido.factura.Nºfactura
                    formaPago.value = pedido.factura.forma_de_pago
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isEditing.value) "✏️ Editar Pedido" else "➕ Crear Nuevo Pedido",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = articulo.value,
            onValueChange = { articulo.value = it },
            label = { Text("Artículo") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = isEditing.value
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = precio.value,
            onValueChange = { precio.value = it },
            label = { Text("Precio (€)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            readOnly = isEditing.value
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Selector de estado
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = estado.value,
                onValueChange = {},
                label = { Text("Estado") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.CheckCircle, contentDescription = "Estado Icon") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Desplegar",
                        modifier = Modifier.clickable { estadoExpanded.value = true }
                    )
                }
            )

            DropdownMenu(
                expanded = estadoExpanded.value,
                onDismissRequest = { estadoExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("PENDIENTE", "PROCESANDO", "ENVIADO", "ENTREGADO", "CANCELADO").forEach { estadoOption ->
                    DropdownMenuItem(
                        text = { Text(estadoOption) },
                        onClick = {
                            estado.value = estadoOption
                            estadoExpanded.value = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nFactura.value,
            onValueChange = { nFactura.value = it },
            label = { Text("Nº Factura") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = isEditing.value
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Selector de forma de pago
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = formaPago.value,
                onValueChange = {},
                label = { Text("Forma de Pago") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Desplegar",
                        modifier = Modifier.clickable { pagoExpanded.value = true }
                    )
                }
            )

            DropdownMenu(
                expanded = pagoExpanded.value,
                onDismissRequest = { pagoExpanded.value = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("TARJETA", "EFECTIVO", "TRANSFERENCIA", "BIZUM").forEach { pagoOption ->
                    DropdownMenuItem(
                        text = { Text(pagoOption) },
                        onClick = {
                            formaPago.value = pagoOption
                            pagoExpanded.value = false
                        }
                    )
                }
            }
        }

        if (errorMessage.value.isNotEmpty()) {
            Text(errorMessage.value, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("⬅️ Volver")
            }

            Button(
                onClick = {
                    if (articulo.value.isBlank()) {
                        errorMessage.value = "⚠️ El artículo no puede estar vacío."
                        return@Button
                    }
                    if (precio.value.isBlank()) {
                        errorMessage.value = "⚠️ El precio no puede estar vacío."
                        return@Button
                    }

                    isLoading.value = true

                    val facturaDTO = FacturaDTO(
                        Nºfactura = nFactura.value,
                        fecha_compra = Date(), // Fecha actual
                        forma_de_pago = formaPago.value
                    )

                    if (isEditing.value) {
                        viewModel.updatePedidoEstado(
                            pedidoId!!,
                            estado.value
                        ) { success ->
                            if (success) {
                                navController.popBackStack()
                            } else {
                                errorMessage.value = "❌ Error al actualizar el pedido."
                            }
                            isLoading.value = false
                        }
                    } else {
                        viewModel.createPedido(
                            articulo.value,
                            estado.value,
                            precio.value.toDouble(),
                            facturaDTO
                        ) { success ->
                            if (success) {
                                navController.popBackStack()
                            } else {
                                errorMessage.value = "❌ Error al crear el pedido."
                            }
                            isLoading.value = false
                        }
                    }
                },
                enabled = !isLoading.value
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                } else {
                    Text(if (isEditing.value) "✅ Actualizar Pedido" else "📌 Crear Pedido")
                }
            }
        }
    }
}