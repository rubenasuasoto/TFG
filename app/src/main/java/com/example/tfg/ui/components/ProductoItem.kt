package com.example.tfg.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tfg.data.models.Producto

@Composable
fun ProductoItem(
    producto: Producto,
    onVerDetalle: () -> Unit,
    onAgregarCarrito: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onVerDetalle() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            producto.imagenUrl?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = producto.articulo,
                    modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = producto.articulo ?: "Sin nombre", style = MaterialTheme.typography.titleMedium)
                producto.descripcion?.let {
                    Text(text = it, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
                Text(text = "Precio: ${producto.precio}€", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Stock: ${producto.stock}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onAgregarCarrito) {
                    Text("Agregar al carrito")
                }
            }
        }
    }
}
