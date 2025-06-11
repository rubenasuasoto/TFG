package com.example.tfg.ui.screens.Menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfg.ui.viewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CambiarPasswordScreen(navController: NavHostController, viewModel: AuthViewModel) {
    var actual by remember { mutableStateOf("") }
    var nueva by remember { mutableStateOf("") }
    var repetir by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    var nuevaError by remember { mutableStateOf(false) }
    var repetirError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cambiar contraseña") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = actual,
                onValueChange = { actual = it },
                label = { Text("Contraseña actual") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nueva,
                onValueChange = {
                    nueva = it
                    nuevaError = false
                },
                label = { Text("Nueva contraseña") },
                isError = nuevaError,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            if (nuevaError) {
                Text(
                    "Debe tener al menos 8 caracteres, incluir un número y un carácter especial",
                    color = MaterialTheme.colorScheme.error
                )
            }

            OutlinedTextField(
                value = repetir,
                onValueChange = {
                    repetir = it
                    repetirError = false
                },
                label = { Text("Repetir nueva contraseña") },
                isError = repetirError,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            if (repetirError) {
                Text("Las contraseñas no coinciden", color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    nuevaError = nueva.length < 8 || !nueva.any { it.isDigit() } || !nueva.any { !it.isLetterOrDigit() }
                    repetirError = nueva != repetir

                    if (nuevaError || repetirError) return@Button

                    loading = true
                    viewModel.cambiarPassword(actual, nueva) { success, msg ->
                        mensaje = msg
                        loading = false
                        if (success) {
                            actual = ""
                            nueva = ""
                            repetir = ""
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !loading
            ) {
                if (loading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                } else {
                    Text("Cambiar contraseña")
                }
            }

            if (mensaje.isNotBlank()) {
                Text(
                    mensaje,
                    color = if (mensaje.startsWith("✅")) Color(0xFF2E7D32) else MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
