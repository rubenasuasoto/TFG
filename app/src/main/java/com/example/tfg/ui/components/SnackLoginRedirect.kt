package com.example.tfg.ui.components



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SnackLoginRedirect(
    navController: NavHostController,
    message: String,
    onConfirm: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var shown by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!shown) {
            shown = true
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "Iniciar sesión",
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.ActionPerformed) {
                onConfirm()
            } else {
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        // Aquí se usa el parámetro para evitar el warning
        Box(modifier = Modifier.padding(paddingValues)) {}
    }
}



