package com.example.tfg.ui.screens.inicio_sesion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tfg.data.models.Direccion
import com.example.tfg.ui.navigation.AppScreen
import com.example.tfg.ui.viewModel.AuthViewModel
@Composable
fun RegisterScreen(navController: NavHostController, viewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var calle by remember { mutableStateOf("") }
    var num by remember { mutableStateOf("") }
    var cp by remember { mutableStateOf("") }
    var municipio by remember { mutableStateOf("") }
    var provincia by remember { mutableStateOf("") }

    var usernameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var passwordMatchError by remember { mutableStateOf(false) }

    val registerResponse = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    val USERNAME_REGEX = Regex("^[a-zA-Z0-9._-]{3,20}$")
    val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                usernameError = !USERNAME_REGEX.matches(it)
            },
            label = { Text("Usuario") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            isError = usernameError,
            modifier = Modifier.fillMaxWidth()
        )
        if (usernameError) Text("Usuario inválido. 3-20 caracteres: letras, números, '.', '-' o '_'", color = Color.Red)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = !EMAIL_REGEX.matches(it)
            },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            isError = emailError,
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError) Text("Email inválido", color = Color.Red)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = it.length < 8 || !it.any { c -> c.isDigit() } || !it.any { c -> !c.isLetterOrDigit() }
                passwordMatchError = (passwordRepeat.isNotEmpty() && password != passwordRepeat)
            },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordError) Text("Contraseña débil. Mínimo 8 caracteres, 1 número y 1 símbolo", color = Color.Red)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = passwordRepeat,
            onValueChange = {
                passwordRepeat = it
                passwordMatchError = it != password
            },
            label = { Text("Repetir contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordMatchError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordMatchError) Text("Las contraseñas no coinciden", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = calle,
            onValueChange = { calle = it },
            label = { Text("Calle") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = num,
            onValueChange = { num = it },
            label = { Text("Número") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cp,
            onValueChange = { cp = it },
            label = { Text("Código Postal") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = municipio,
            onValueChange = { municipio = it },
            label = { Text("Municipio") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = provincia,
            onValueChange = { provincia = it },
            label = { Text("Provincia") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (
                        username.isNotBlank() && email.isNotBlank() &&
                        password.isNotBlank() && passwordRepeat.isNotBlank() &&
                        calle.isNotBlank() && num.isNotBlank() &&
                        municipio.isNotBlank() && provincia.isNotBlank() && cp.isNotBlank()
                    ) {
                        if (password == passwordRepeat) {
                            isLoading.value = true
                            val direccion = Direccion(calle, num, municipio, provincia, cp)

                            viewModel.register(username, email, password, passwordRepeat, direccion) { success, message ->
                                registerResponse.value = message
                                isLoading.value = false
                                if (success) {
                                    navController.navigate(AppScreen.Login.route) {
                                        popUpTo(AppScreen.Registro.route) { inclusive = true }
                                    }
                                }
                            }
                        } else {
                            registerResponse.value = "❌ Corrige los errores antes de continuar."
                        }
                    } else {
                        registerResponse.value = "❌ Todos los campos son obligatorios."
                    }
                },
                enabled = !isLoading.value,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading.value) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                else Text("Registrarse")
            }

        if (registerResponse.value.isNotEmpty()) {
            Text(
                text = registerResponse.value,
                color = if (registerResponse.value.startsWith("✅")) Color.Green else Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        TextButton(onClick = { navController.navigate("login") }) {
            Text("¿Ya tienes cuenta? Inicia sesión", color = MaterialTheme.colorScheme.primary)
        }
    }
}
