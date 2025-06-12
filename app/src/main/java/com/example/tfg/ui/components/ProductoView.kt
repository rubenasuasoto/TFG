package com.example.tfg.ui.components

import androidx.compose.foundation.background
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
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        producto.imagenUrl?.let {
            AsyncImage(
                model = it,
                contentDescription = producto.articulo,
                contentScale = ContentScale.Fit, // 🖼️ Mejora: evita recorte de imagen
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 240.dp, max = 300.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
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

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(Strings.precioLabel, style = MaterialTheme.typography.titleMedium)
            Text("€${"%.2f".format(producto.precio)}", style = MaterialTheme.typography.titleMedium)
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(Strings.stockDisponibleLabel, style = MaterialTheme.typography.bodyMedium)
            Text("${producto.stock}", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (isUserLoggedIn) onAgregarYIrCarrito() else onLoginRedirect()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = producto.stock > 0
        ) {
            Text(if (producto.stock > 0) Strings.agregarAlCarrito else Strings.sinStock)
        }

        Divider(thickness = 1.dp)

        // 📋 Simulación de especificaciones
        Text("Especificaciones del producto", style = MaterialTheme.typography.titleMedium)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("• Categoría: Electrónica")
            Text("• Dimensiones: ")
            Text("• Peso: ")
            Text("• Garantía: 2 años")
        }

        Divider(thickness = 1.dp)

        // 💬 Simulación de comentarios
        Text("Comentarios", style = MaterialTheme.typography.titleMedium)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("⭐️⭐️⭐️⭐️⭐️ \"Excelente calidad. Lo recomiendo!\" – Juan P.")
            Text("⭐️⭐️⭐️⭐️ \"Funciona bien, cumple su función.\" – Laura G.")
            Text("⭐️⭐️⭐️ \"Esperaba más, pero está bien por el precio.\" – Carlos M.")
        }
        Spacer(modifier = Modifier.height(80.dp)) // evita solapamiento con el BottomBar

    }
}
