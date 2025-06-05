package com.example.tfg.Navegacion

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tfg.screens.LoginScreen
import com.example.tfg.screens.PedidoFormScreen
import com.example.tfg.screens.PedidoScreen
import com.example.tfg.screens.RegisterScreen
import com.example.tfg.viewModel.AuthViewModel
import com.example.tfg.viewModel.Factory.AuthViewModelFactory
import com.example.tfg.viewModel.Factory.PedidoViewModelFactory
import com.example.tfg.viewModel.PedidoViewModel

@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    val application = context.applicationContext as Application // ✅ Obtener Application
    val pedidoViewModel: PedidoViewModel = viewModel(factory = PedidoViewModelFactory(application))
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context, pedidoViewModel) // 🔹 Pasar TareaViewModel
    )

    NavHost(navController = navController, startDestination = AppScreen.Login.route) {
        composable(AppScreen.Registro.route) {
            RegisterScreen(navController, authViewModel)
        }
        composable(AppScreen.Login.route) {
            LoginScreen(navController, authViewModel)
        }
        composable(AppScreen.Tareas.route) {
            PedidoScreen(navController, pedidoViewModel)
        }
        composable("tarea_form/{tareaId}") { backStackEntry ->
            val tareaId = backStackEntry.arguments?.getString("tareaId") ?: "new"
            PedidoFormScreen(navController, pedidoViewModel, tareaId)
        }
    }
}