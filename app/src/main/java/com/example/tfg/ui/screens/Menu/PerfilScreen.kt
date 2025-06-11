package com.example.tfg.ui.screens.Menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.tfg.data.models.Direccion
import com.example.tfg.data.models.UsuarioUpdateDTO
import com.example.tfg.ui.components.BottomBarNavigation
import com.example.tfg.ui.navigation.AppScreen
import com.example.tfg.ui.viewModel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val usuario by authViewModel.usuarioActual.collectAsState()

    var email by remember { mutableStateOf("") }
    var calle by remember { mutableStateOf("") }
    var municipio by remember { mutableStateOf("") }
    var provincia by remember { mutableStateOf("") }
    var cp by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var provinciaError by remember { mutableStateOf(false) }

    val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    val isEditing = remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        authViewModel.cargarPerfil()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Mi perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            BottomBarNavigation(
                navController = navController,
                onCarritoClick = { navController.navigate(AppScreen.Carrito.route) },
                onMenuClick = { navController.navigate(AppScreen.Menu.route) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            usuario?.let {
                if (!isEditing.value) {
                    // Modo visual
                    Text("Nombre de usuario: ${it.username}", style = MaterialTheme.typography.titleMedium)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Email: ${it.email}", modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            email = it.email
                            isEditing.value = true
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar email")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Dirección:", style = MaterialTheme.typography.titleMedium)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Calle: ${it.direccion.calle}", modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            calle = it.direccion.calle
                            municipio = it.direccion.municipio
                            provincia = it.direccion.provincia
                            cp = it.direccion.cp
                            email = it.email
                            isEditing.value = true
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar dirección")
                        }
                    }

                    Text("Municipio: ${it.direccion.municipio}")
                    Text("Provincia: ${it.direccion.provincia}")
                    Text("Código postal: ${it.direccion.cp}")
                } else {
                    // Modo edición
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = !EMAIL_REGEX.matches(it)
                        },
                        label = { Text("Email") },
                        isError = emailError,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (emailError) Text("Email inválido", color = Color.Red)

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Dirección:", style = MaterialTheme.typography.titleMedium)

                    OutlinedTextField(
                        value = calle,
                        onValueChange = { calle = it },
                        label = { Text("Calle") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = municipio,
                        onValueChange = { municipio = it },
                        label = { Text("Municipio") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = provincia,
                        onValueChange = {
                            provincia = it
                            provinciaError = it.isBlank()
                        },
                        label = { Text("Provincia") },
                        isError = provinciaError,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (provinciaError) Text("La provincia no puede estar vacía", color = Color.Red)

                    OutlinedTextField(
                        value = cp,
                        onValueChange = { cp = it },
                        label = { Text("Código Postal") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            onClick = {
                                emailError = !EMAIL_REGEX.matches(email)
                                provinciaError = provincia.isBlank()

                                val camposValidos = !emailError && !provinciaError

                                if (!camposValidos) {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("❌ Corrige los errores antes de guardar")
                                    }
                                    return@Button
                                }

                                val dto = UsuarioUpdateDTO(
                                    currentPassword = null,
                                    newPassword = null,
                                    email = email,
                                    rol = null,
                                    direccion = Direccion(calle, it.direccion.num, municipio, provincia, cp)
                                )

                                authViewModel.actualizarPerfil(dto) { success, msg ->
                                    scope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = msg,
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                    if (success) {
                                        isEditing.value = false
                                        authViewModel.cargarPerfil()
                                    }
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Guardar")
                        }

                        OutlinedButton(
                            onClick = { isEditing.value = false },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancelar")
                        }
                    }
                }
            } ?: run {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
