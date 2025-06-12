package com.example.tfg.ui.navigation

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tfg.ui.screens.inicio_sesion.LoginScreen
import com.example.tfg.ui.screens.inicio_sesion.RegisterScreen
import com.example.tfg.ui.screens.home.HomeScreen
import com.example.tfg.ui.viewModel.AuthViewModel
import com.example.tfg.ui.viewModel.Factory.AuthViewModelFactory
import com.example.tfg.ui.viewModel.Factory.PedidoViewModelFactory
import com.example.tfg.ui.viewModel.Factory.ProductoViewModelFactory
import com.example.tfg.ui.viewModel.PedidoViewModel
import com.example.tfg.ui.viewModel.ProductoViewModel
import androidx.compose.runtime.getValue
import com.example.tfg.ui.components.SnackLoginRedirect
import com.example.tfg.ui.screens.AdminPersonalizationScreen
import com.example.tfg.ui.screens.CarritoScreen

import com.example.tfg.ui.screens.Menu.CambiarPasswordScreen
import com.example.tfg.ui.screens.Menu.MenuScreen
import com.example.tfg.ui.screens.Menu.PedidosScreen
import com.example.tfg.ui.screens.Menu.PerfilScreen
import com.example.tfg.ui.screens.ProductoScreen
import com.example.tfg.ui.screens.SettingsScreen
import com.example.tfg.ui.screens.admin.AdminDashboardScreen
import com.example.tfg.ui.screens.admin.AdminPedidosScreen
import com.example.tfg.ui.screens.admin.AdminProductosScreen
import com.example.tfg.ui.screens.admin.AdminUsuariosScreen
import com.example.tfg.ui.viewModel.AppSettingsViewModel
import com.example.tfg.ui.viewModel.AuthViewModel.AuthState
import com.example.tfg.ui.viewModel.GlobalStyleViewModel


@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    val application = context.applicationContext as Application

    val pedidoViewModel: PedidoViewModel = viewModel(factory = PedidoViewModelFactory(application))
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(context, pedidoViewModel))
    val productoViewModel: ProductoViewModel = viewModel(factory = ProductoViewModelFactory(application))
    val settingsViewModel: AppSettingsViewModel = viewModel()
    val globalStyleViewModel: GlobalStyleViewModel = viewModel()

    // Observa los estados
    val loginState by authViewModel.loginState.collectAsState()
    val isAdmin by authViewModel.isAdmin.collectAsState()

    // Determina si el usuario está logueado
    val isUserLoggedIn = loginState is AuthState.Success

    // Ejecuta verificación del token existente solo una vez
    LaunchedEffect(Unit) {
        authViewModel.checkExistingToken()
    }

    // Asegura sincronización con PedidoViewModel
    LaunchedEffect(isAdmin) {
        pedidoViewModel.setAdminStatus(isAdmin)
    }

    NavHost(navController = navController, startDestination = AppScreen.Home.route) {
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
                onAddToCart = { /* tu lógica aquí */ },
                isUserLoggedIn = isUserLoggedIn
            )
        }
        composable("${AppScreen.DetalleProducto.route}/{numeroProducto}") { backStackEntry ->
            val numeroProducto = backStackEntry.arguments?.getString("numeroProducto") ?: ""
            ProductoScreen(
                numeroProducto = numeroProducto,
                navController = navController,
                productoViewModel = productoViewModel,
                onAgregarCarrito = { producto -> pedidoViewModel.agregarProducto(producto) },
                isUserLoggedIn = isUserLoggedIn
            )
        }
        composable(AppScreen.Menu.route) {
            if (isUserLoggedIn) {
                MenuScreen(navController, authViewModel)
            } else {
                SnackLoginRedirect(
                    navController = navController,
                    message = "Debes iniciar sesión para acceder al menú",
                    onConfirm = { navController.navigate(AppScreen.Login.route) }
                )
            }
        }


        composable(AppScreen.Carrito.route) {
            if (isUserLoggedIn) {
                CarritoScreen(navController, pedidoViewModel, isUserLoggedIn)
            } else {
                SnackLoginRedirect(
                    navController = navController,
                    message = "Necesitas iniciar sesión para ver tu carrito",
                    onConfirm = { navController.navigate(AppScreen.Login.route) }
                )
            }
        }
        composable(AppScreen.Perfil.route) {
            PerfilScreen(navController, authViewModel)
        }
        composable(AppScreen.Pedidos.route) {
            PedidosScreen(navController, pedidoViewModel, isUserLoggedIn)
        }
        composable(AppScreen.Cambiarcontrasena.route) {
            CambiarPasswordScreen(navController, authViewModel)
        }
        composable(AppScreen.AdminUsuarios.route) {
            AdminUsuariosScreen(navController, authViewModel)
        }
        composable(AppScreen.AdminPedidos.route) {
            AdminPedidosScreen(navController, pedidoViewModel)
        }

        composable(AppScreen.AdminProductos.route) {
            AdminProductosScreen(navController, productoViewModel)
        }
        composable(AppScreen.AdminDashboard.route) {
            AdminDashboardScreen(
                productoViewModel = productoViewModel,
                pedidoViewModel = pedidoViewModel,
                authViewModel = authViewModel,
                navController = navController
            )
        }
        composable(AppScreen.Settings.route) {
            SettingsScreen(navController,settingsViewModel)


        }
        composable(AppScreen.AdminPersonalization.route) {
            AdminPersonalizationScreen(navController, globalStyleViewModel
            , settingsViewModel)
        }










    }
}