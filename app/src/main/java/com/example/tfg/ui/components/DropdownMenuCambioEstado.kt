package com.example.tfg.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.tfg.ui.viewModel.PedidoViewModel

@Composable
fun DropdownMenuCambioEstado(numeroPedido: String, estadoActual: String, viewModel: PedidoViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val opciones = listOf("PENDIENTE", "COMPLETADO", "CANCELADO").filter { it != estadoActual }

    Box {
        Button(onClick = { expanded = true }) {
            Text("Cambiar estado")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            opciones.forEach { nuevoEstado ->
                DropdownMenuItem(
                    text = { Text(nuevoEstado) },
                    onClick = {
                        expanded = false
                        viewModel.cambiarEstadoPedido(numeroPedido, nuevoEstado)
                    }
                )
            }
        }
    }
}
