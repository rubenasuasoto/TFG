package com.example.tfg.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tfg.data.models.Producto

@Composable
fun ProductoView(
    producto: Producto,
    isUserLoggedIn: Boolean,
    onAgregarYIrCarrito: () -> Unit,
    onLoginRedirect: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        producto.imagenUrl?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = producto.articulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = producto.articulo ?: "Sin nombre",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        producto.descripcion?.let {
            Text(text = it, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(text = "Precio: €${producto.precio}", style = MaterialTheme.typography.titleMedium)
        Text(text = "Stock: ${producto.stock}", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (isUserLoggedIn) onAgregarYIrCarrito()
                else onLoginRedirect()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar al carrito")
        }
    }
}
