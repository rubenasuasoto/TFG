package com.example.tfg.ui.screens.admin

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.tfg.data.models.Producto
import com.example.tfg.ui.viewModel.ProductoViewModel
import com.example.tfg.utils.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminProductosScreen(
    navController: NavHostController,
    productoViewModel: ProductoViewModel
) {
    val productos by productoViewModel.productos.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var productoAEditar by remember { mutableStateOf<Producto?>(null) }
    var mostrarFormulario by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        productoViewModel.fetchAllProductos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Strings.adminProductosTitulo) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = Strings.volver)
                    }
                },
                actions = {
                    IconButton(onClick = { productoAEditar = null; mostrarFormulario = true }) {
                        Icon(Icons.Default.Add, contentDescription = Strings.nuevo)
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
                label = { Text(Strings.buscarProducto) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            val filtrados = productos.filter {
                it.articulo?.contains(searchQuery, ignoreCase = true) == true
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filtrados) { producto ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(producto.articulo ?: Strings.sinNombre, style = MaterialTheme.typography.titleMedium)
                            Text("${Strings.precio} : ${producto.precio}")
                            Text("${Strings.stock} : ${producto.stock}")

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                OutlinedButton(onClick = {
                                    productoAEditar = producto
                                    mostrarFormulario = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(Strings.editar)
                                }

                                OutlinedButton(onClick = {
                                    producto.numeroProducto.let {
                                        productoViewModel.deleteProducto(it) {}
                                    }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = null)
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

    if (mostrarFormulario) {
        Dialog(onDismissRequest = { mostrarFormulario = false }) {
            Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                ProductoForm(
                    producto = productoAEditar,
                    onGuardar = { producto ->
                        if (producto.numeroProducto.isNotEmpty()) {
                            productoViewModel.updateProducto(producto.numeroProducto, producto) {}
                        } else {
                            productoViewModel.crearProducto(producto) {}
                        }
                        mostrarFormulario = false
                    },
                    onCancelar = { mostrarFormulario = false }
                )
            }
        }
    }
}
@Composable
fun ProductoForm(
    producto: Producto?,
    onGuardar: (Producto) -> Unit,
    onCancelar: () -> Unit
) {
    var articulo by remember { mutableStateOf(producto?.articulo ?: "") }
    var precio by remember { mutableStateOf(producto?.precio?.toString() ?: "") }
    var stock by remember { mutableStateOf(producto?.stock?.toString() ?: "") }
    var imagenUrl by remember { mutableStateOf(producto?.imagenUrl ?: "") }

    var articuloError by remember { mutableStateOf(false) }
    var precioError by remember { mutableStateOf(false) }
    var stockError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(Strings.formularioProducto, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = articulo,
            onValueChange = {
                articulo = it
                articuloError = it.isBlank()
            },
            label = { Text(Strings.nombreProducto) },
            isError = articuloError,
            modifier = Modifier.fillMaxWidth()
        )
        if (articuloError) Text(Strings.campoObligatorio, color = Color.Red)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = precio,
            onValueChange = {
                precio = it
                precioError = it.toDoubleOrNull()?.let { v -> v <= 0 } ?: true
            },
            label = { Text(Strings.precio) },
            isError = precioError,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (precioError) Text(Strings.precioInvalido, color = Color.Red)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = stock,
            onValueChange = {
                stock = it
                stockError = it.toIntOrNull()?.let { v -> v < 0 } ?: true
            },
            label = { Text(Strings.stock) },
            isError = stockError,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (stockError) Text(Strings.stockInvalido, color = Color.Red)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = imagenUrl,
            onValueChange = { imagenUrl = it },
            label = { Text(Strings.urlImagen) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                articuloError = articulo.isBlank()
                precioError = precio.toDoubleOrNull()?.let { it <= 0 } ?: true
                stockError = stock.toIntOrNull()?.let { it < 0 } ?: true

                val esValido = !articuloError && !precioError && !stockError

                if (esValido) {
                    val nuevo = Producto(
                        numeroProducto = producto?.numeroProducto ?: "",
                        articulo = articulo,
                        precio = precio.toDouble(),
                        stock = stock.toInt(),
                        imagenUrl = imagenUrl.ifBlank { null }
                    )
                    onGuardar(nuevo)
                }
            }) {
                Text(Strings.guardar)
            }

            OutlinedButton(onClick = onCancelar) {
                Text(Strings.cancelar)
            }
        }
    }
}
