package com.example.tfg.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tfg.ui.viewModel.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel) {}
/*
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var calle by remember { mutableStateOf("") }
    var num by remember { mutableStateOf("") }
    var cp by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    val registerResponse = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Permite desplazarse si hay muchos campos
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Register Icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Registro",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Datos de usuario", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "User Icon") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Lock Icon") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = passwordRepeat,
                    onValueChange = { passwordRepeat = it },
                    label = { Text("Repeat Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Lock Icon") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Dirección", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = calle,
                    onValueChange = { calle = it },
                    label = { Text("Calle") },
                    leadingIcon = { Icon(Icons.Default.Home, contentDescription = "Address Icon") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = num,
                    onValueChange = { num = it },
                    label = { Text("Número") },
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = "Number Icon") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cp,
                    onValueChange = { cp = it },
                    label = { Text("Código Postal") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = "Postal Code Icon") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = ciudad,
                    onValueChange = { ciudad = it },
                    label = { Text("Ciudad") },
                    leadingIcon = { Icon(Icons.Default.Place, contentDescription = "City Icon") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && passwordRepeat.isNotBlank() &&
                    calle.isNotBlank() && num.isNotBlank() && cp.isNotBlank() && ciudad.isNotBlank()) {

                    if (password == passwordRepeat) {
                        isLoading.value = true

                        val direccion = Direccion(calle, num, cp, ciudad)

                        viewModel.register(username, email, password, passwordRepeat, "user", direccion) {
                            registerResponse.value = it
                            isLoading.value = false
                        }
                    } else {
                        registerResponse.value = "❌ Las contraseñas no coinciden."
                    }
                } else {
                    registerResponse.value = "❌ Todos los campos son obligatorios."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading.value,
            shape = RoundedCornerShape(12.dp)
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text("Registrarse", fontSize = 18.sp)
            }
        }

        if (registerResponse.value.isNotEmpty()) {
            Text(
                registerResponse.value,
                color = if (registerResponse.value.startsWith("✅")) Color.Green else Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        TextButton(onClick = { navController.navigate("login") }) {
            Text("¿Ya tienes cuenta? Inicia sesión aquí", color = MaterialTheme.colorScheme.secondary)
        }
    }
}
*/