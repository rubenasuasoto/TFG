package com.example.tfg.ui.navigation

sealed class AppScreen(val route: String) {
    data object Login : AppScreen("login")
    data object Registro : AppScreen("registro")
    data object Home : AppScreen("home")
    data object DetalleProducto : AppScreen("detalle_producto")
    data object Menu : AppScreen("menu")
    data object Carrito : AppScreen("carrito")
    data object Perfil : AppScreen("perfil")
    data object Pedidos : AppScreen("pedidos")
    data object Cambiarcontrasena : AppScreen("cambiar_contrasena")


}

