package com.example.tfg.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.example.tfg.ui.viewModel.AppSettingsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    settingsViewModel: AppSettingsViewModel
) {
    val isDark by settingsViewModel.isDarkTheme.collectAsState()
    val scale by settingsViewModel.fontScale.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ajustes") },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Modo oscuro", modifier = Modifier.weight(1f))
                Switch(checked = isDark, onCheckedChange = { settingsViewModel.toggleDarkTheme() })
            }

            Text("Tamaño de letra")

            val opciones = listOf(
                "Pequeño" to 0.85f,
                "Medio" to 1.0f,
                "Grande" to 1.25f
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                opciones.forEach { (label, value) ->
                    val seleccionado = scale == value
                    OutlinedButton(
                        onClick = { settingsViewModel.setFontScale(value) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (seleccionado) MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.15f
                            )
                            else Color.Transparent
                        )
                    ) {
                        Text(label)
                    }
                }
            }
        }
    }
}
