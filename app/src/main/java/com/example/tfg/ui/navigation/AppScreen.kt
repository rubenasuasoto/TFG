package com.example.tfg.ui.navigation

sealed class AppScreen(val route: String) {
    data object Login : AppScreen("login")
    data object Registro : AppScreen("registro")
    data object Home : AppScreen("home")
    data object DetalleProducto : AppScreen("producto")
    data object Menu : AppScreen("menu")
    data object Carrito : AppScreen("carrito")
    data object Perfil : AppScreen("perfil")
    data object Pedidos : AppScreen("pedidos")
    data object Cambiarcontrasena : AppScreen("cambiar_contrasena")
    data object AdminProductos : AppScreen("admin_productos")
    data object AdminPedidos : AppScreen("admin_pedidos")
    data object AdminUsuarios : AppScreen("admin_usuarios")
    data object AdminDashboard : AppScreen("admin_dashboard")
    data object Settings : AppScreen("settings")
    data object AdminPersonalization : AppScreen("admin_personalizacion")



}

