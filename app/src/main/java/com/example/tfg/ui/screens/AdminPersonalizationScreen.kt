package com.example.tfg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfg.ui.viewModel.GlobalStyleViewModel
import androidx.core.graphics.toColorInt
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import com.example.tfg.ui.viewModel.AppSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPersonalizationScreen(
    navController: NavHostController,
    globalStyleViewModel: GlobalStyleViewModel,
    settingsViewModel: AppSettingsViewModel
) {
    val currentColor by globalStyleViewModel.primaryColorHex.collectAsState()
    val isDark by settingsViewModel.isDarkTheme.collectAsState()

    // Paletas según modo
    val coloresDisponibles = if (isDark) {
        listOf("#2196F3", "#64DD17", "#FFEB3B", "#F50057", "#00BCD4")
    } else {
        listOf("#1976D2", "#388E3C", "#FB8C00", "#7B1FA2", "#546E7A")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personalización de App") },
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text("Color principal actual:", style = MaterialTheme.typography.titleMedium)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(currentColor.toColorInt()))
                    .border(1.dp, MaterialTheme.colorScheme.outline)
            )

            Divider()

            Text("Selecciona un nuevo color:", style = MaterialTheme.typography.titleMedium)

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                coloresDisponibles.chunked(3).forEach { fila ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        fila.forEach { hex ->
                            val color = Color(hex.toColorInt())
                            val isSelected = hex.equals(currentColor, ignoreCase = true)

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .border(
                                        width = if (isSelected) 3.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(color)
                                    .clickable { globalStyleViewModel.setPrimaryColor(hex) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Este color se aplicará a toda la aplicación para todos los usuarios.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
