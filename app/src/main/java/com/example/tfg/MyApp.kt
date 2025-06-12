package com.example.tfg

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.example.tfg.ui.navigation.AppNavigation
import com.example.tfg.ui.viewModel.AppSettingsViewModel

import androidx.compose.runtime.getValue

@Composable
fun MyApp(settingsViewModel: AppSettingsViewModel) {
    val isDark by settingsViewModel.isDarkTheme.collectAsState()
    val fontScale by settingsViewModel.fontScale.collectAsState()

    CompositionLocalProvider(LocalDensity provides Density(LocalDensity.current.density, fontScale)) {
        MaterialTheme(
            colorScheme = if (isDark) darkColorScheme() else lightColorScheme()
        ) {
            AppNavigation(context = LocalContext.current)
        }
    }
}
