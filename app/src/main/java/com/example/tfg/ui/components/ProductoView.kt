package com.example.tfg.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tfg.data.models.Producto
import com.example.tfg.utils.Strings

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
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        producto.imagenUrl?.let {
            AsyncImage(
                model = it,
                contentDescription = producto.articulo,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Text(
            text = producto.articulo ?: Strings.sinNombre,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        producto.descripcion?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Divider()

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(Strings.precioLabel, style = MaterialTheme.typography.titleMedium)
            Text("€${"%.2f".format(producto.precio)}", style = MaterialTheme.typography.titleMedium)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(Strings.stockDisponibleLabel, style = MaterialTheme.typography.bodyMedium)
            Text("${producto.stock}", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (isUserLoggedIn) onAgregarYIrCarrito() else onLoginRedirect()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = producto.stock > 0
        ) {
            Text(
                text = if (producto.stock > 0)
                    Strings.agregarAlCarrito
                else
                    Strings.sinStock
            )
        }
    }
}
