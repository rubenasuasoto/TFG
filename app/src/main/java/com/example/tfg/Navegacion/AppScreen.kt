package com.example.tfg.Navegacion

sealed class AppScreen(val route: String) {
    data object Login : AppScreen("login")
    data object Registro : AppScreen("registro")
    data object Tareas : AppScreen("tareas")
    data object TareaForm : AppScreen("tarea_form/{tareaId}")
}

