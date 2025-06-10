package com.example.tfg.ui.navigation

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tfg.ui.screens.DetalleProductoScreen
import com.example.tfg.ui.screens.LoginScreen
import com.example.tfg.ui.screens.PedidoScreen
import com.example.tfg.ui.screens.RegisterScreen
import com.example.tfg.ui.screens.home.HomeScreen
import com.example.tfg.ui.viewModel.AuthViewModel
import com.example.tfg.ui.viewModel.Factory.AuthViewModelFactory
import com.example.tfg.ui.viewModel.Factory.PedidoViewModelFactory
import com.example.tfg.ui.viewModel.Factory.ProductoViewModelFactory
import com.example.tfg.ui.viewModel.PedidoViewModel
import com.example.tfg.ui.viewModel.ProductoViewModel
import androidx.compose.runtime.getValue


@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    val application = context.applicationContext as Application

    val pedidoViewModel: PedidoViewModel = viewModel(factory = PedidoViewModelFactory(application))
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(context, pedidoViewModel))
    val productoViewModel: ProductoViewModel = viewModel(factory = ProductoViewModelFactory(application))
    val isAdmin by authViewModel.isAdmin.collectAsState()

    // Sincronización por si inicia directamente con token guardado
    LaunchedEffect(isAdmin) {
        pedidoViewModel.setAdminStatus(isAdmin)
        authViewModel.checkExistingToken()
    }

    NavHost(navController = navController, startDestination = AppScreen.Login.route) {
        composable(AppScreen.Registro.route) {
            RegisterScreen(navController, authViewModel)
        }
        composable(AppScreen.Login.route) {
            LoginScreen(navController, authViewModel)
        }
        composable(AppScreen.Home.route) {
            HomeScreen(
                navController,
                productoViewModel = productoViewModel.apply { fetchAllProductos() },
                onAddToCart = { /* tu lógica */ },
                isUserLoggedIn = true // o según tu lógica de token
            )
        }

    }
}
