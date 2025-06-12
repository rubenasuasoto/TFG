package com.example.tfg.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import com.example.tfg.ui.components.FilaColores
import com.example.tfg.ui.viewModel.AppSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPersonalizationScreen(
    navController: NavHostController,
    globalStyleViewModel: GlobalStyleViewModel,
    settingsViewModel: AppSettingsViewModel
) {
    val isDark by settingsViewModel.isDarkTheme.collectAsState()
    val lightHex by globalStyleViewModel.lightColor.collectAsState()
    val darkHex by globalStyleViewModel.darkColor.collectAsState()

    val coloresClaro = listOf("#4F8EF7", "#43A047", "#FB6F51", "#9575CD", "#90A4AE")


    val coloresOscuro = listOf("#82B1FF", "#B2FF59", "#F48FB1", "#FFF176", "#80DEEA")



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personalización de la App") },
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
            Text("Colores actuales:", style = MaterialTheme.typography.titleMedium)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .background(Color(lightHex.toColorInt()))
                        .border(1.dp, MaterialTheme.colorScheme.outline),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Claro", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .background(Color(darkHex.toColorInt()))
                        .border(1.dp, MaterialTheme.colorScheme.outline),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Oscuro", style = MaterialTheme.typography.bodyMedium, color = Color.White)
                }
            }

            Divider()

            Text("Seleccionar color para Modo CLARO:", style = MaterialTheme.typography.titleSmall)
            FilaColores(
                actual = lightHex,
                opciones = coloresClaro,
                onSeleccionar = { globalStyleViewModel.setLightPrimaryColor(it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Seleccionar color para Modo OSCURO:", style = MaterialTheme.typography.titleSmall)
            FilaColores(
                actual = darkHex,
                opciones = coloresOscuro,
                onSeleccionar = { globalStyleViewModel.setDarkPrimaryColor(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Los colores personalizados afectan globalmente la app para todos los usuarios.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
