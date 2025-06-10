package com.example.tfg.ui.navigation

sealed class AppScreen(val route: String) {
    data object Login : AppScreen("login")
    data object Registro : AppScreen("registro")
    data object Home : AppScreen("home")
    data object Detalle : AppScreen("detalle/{numeroProducto}") {
        fun createRoute(numeroProducto: String) = "detalle/$numeroProducto"
    }

}

