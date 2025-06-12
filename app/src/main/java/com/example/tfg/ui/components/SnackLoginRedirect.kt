package com.example.tfg.ui.components



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfg.utils.Strings

@Composable
fun SnackLoginRedirect(
    navController: NavHostController,
    message: String = Strings.debesIniciarSesion,
    onConfirm: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var shown by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!shown) {
            shown = true
            val result = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = Strings.iniciarSesion,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.ActionPerformed) {
                onConfirm()
            }
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}



