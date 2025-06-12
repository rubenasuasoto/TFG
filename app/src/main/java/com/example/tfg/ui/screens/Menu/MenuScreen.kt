package com.example.tfg.ui.screens.Menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfg.ui.components.BottomBarNavigation
import com.example.tfg.ui.viewModel.AuthViewModel
import com.example.tfg.ui.navigation.AppScreen
import com.example.tfg.utils.Strings

@Composable
fun MenuScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    Scaffold(
        bottomBar = {
            BottomBarNavigation(
                navController = navController,
                onCarritoClick = { navController.navigate(AppScreen.Carrito.route) },
                onMenuClick = { navController.navigate(AppScreen.Menu.route) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(Strings.menuOpciones, style = MaterialTheme.typography.titleLarge)

            Divider()

            Text(Strings.perfil, modifier = Modifier.clickable {
                navController.navigate(AppScreen.Perfil.route)
            })

            Text(Strings.pedidos, modifier = Modifier.clickable {
                navController.navigate(AppScreen.Pedidos.route)
            })

            Text(Strings.cambiarPasswordTitulo, modifier = Modifier.clickable {
                navController.navigate(AppScreen.Cambiarcontrasena.route)
            })

            Text(Strings.ajustes, modifier = Modifier.clickable {
                navController.navigate(AppScreen.Settings.route)
            })

            if (authViewModel.isAdmin.collectAsState().value) {
                Divider()
                Text("ADMIN", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(8.dp))

                Text(Strings.adminGestionProductos, modifier = Modifier.clickable {
                    navController.navigate(AppScreen.AdminProductos.route)
                })

                Text(Strings.adminGestionPedidos, modifier = Modifier.clickable {
                    navController.navigate(AppScreen.AdminPedidos.route)
                })

                Text(Strings.adminGestionUsuarios, modifier = Modifier.clickable {
                    navController.navigate(AppScreen.AdminUsuarios.route)
                })

                Text(Strings.adminDashboard, modifier = Modifier.clickable {
                    navController.navigate(AppScreen.AdminDashboard.route)
                })

                Text(Strings.adminPersonalizacion, modifier = Modifier.clickable {
                    navController.navigate(AppScreen.AdminPersonalization.route)
                })
            }

            Text(
                Strings.cerrarSesion,
                modifier = Modifier.clickable {
                    authViewModel.logout()
                    navController.navigate(AppScreen.Login.route) {
                        popUpTo(0)
                    }
                },
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
