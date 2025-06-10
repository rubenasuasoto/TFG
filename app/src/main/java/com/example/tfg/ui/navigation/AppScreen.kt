package com.example.tfg.ui.navigation

sealed class AppScreen(val route: String) {
    data object Login : AppScreen("login")
    data object Registro : AppScreen("registro")
    data object Home : AppScreen("home")
    data object DetalleProducto : AppScreen("detalle_producto")
    data object Menu : AppScreen("menu")
    data object Carrito : AppScreen("carrito")


}

