package com.example.tfg.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tfg.ui.navigation.AppScreen
import androidx.compose.runtime.getValue
@Composable
fun BottomBarNavigation(
    navController: NavController,
    onCarritoClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    val currentRoute = currentRoute(navController)
    val primary = MaterialTheme.colorScheme.primary
    val contentColor = contentColorFor(primary)

    val items = listOf(
        BottomNavItem("Carrito", Icons.Default.ShoppingCart, AppScreen.Carrito.route, onCarritoClick),
        BottomNavItem("Inicio", Icons.Default.Home, AppScreen.Home.route, onClick = {
            navController.navigate(AppScreen.Home.route) {
                popUpTo(AppScreen.Home.route) { inclusive = true }
                launchSingleTop = true
            }
        }),
        BottomNavItem("Menú", Icons.Default.Menu, AppScreen.Menu.route, onMenuClick)
    )

    NavigationBar(containerColor = primary) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = item.onClick,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = contentColor,
                    unselectedIconColor = contentColor,
                    selectedTextColor = contentColor,
                    unselectedTextColor = contentColor,
                    indicatorColor = Color.Transparent //  elimina sombreado de selección
                )
            )
        }
    }
}


@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val onClick: () -> Unit
)
