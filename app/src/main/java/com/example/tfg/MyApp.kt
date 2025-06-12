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
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.tfg.ui.viewModel.GlobalStyleViewModel
import androidx.core.graphics.toColorInt

@Composable
fun MyApp(
    settingsViewModel: AppSettingsViewModel,
    styleViewModel: GlobalStyleViewModel
) {
    val isDark by settingsViewModel.isDarkTheme.collectAsState()
    val fontScale by settingsViewModel.fontScale.collectAsState()
    val colorHex by styleViewModel.primaryColorHex.collectAsState()

    val customColor = remember(colorHex) {
        Color(colorHex.toColorInt())
    }

    val colorScheme = if (isDark) darkColorScheme(primary = customColor)
    else lightColorScheme(primary = customColor)

    CompositionLocalProvider(LocalDensity provides Density(LocalDensity.current.density, fontScale)) {
        MaterialTheme(colorScheme = colorScheme) {
            AppNavigation(context = LocalContext.current)
        }
    }
}

