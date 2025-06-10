package com.example.tfg.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.tfg.ui.navigation.AppScreen

@Composable
fun BottomBarNavigation(navController: NavController,
                        onCarritoClick: () -> Unit,
                        onMenuClick: () -> Unit) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito") },
            label = { Text("Carrito") },
            selected = false,
            onClick = onCarritoClick// Asegúrate de tener esta ruta
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = false,
            onClick = { navController.navigate(AppScreen.Home.route) }
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Menu, contentDescription = "Menú") },
            label = { Text("Menú") },
            selected = false,
            onClick = onMenuClick

        )
    }
}
