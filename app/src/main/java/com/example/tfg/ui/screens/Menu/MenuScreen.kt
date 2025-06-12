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

@Composable
fun MenuScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    Scaffold(
        bottomBar = { BottomBarNavigation(
            navController = navController,
            onCarritoClick = {
                navController.navigate(AppScreen.Carrito.route)
            },
            onMenuClick = {
                navController.navigate(AppScreen.Menu.route)
            }
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
            Text("Menú de Opciones", style = MaterialTheme.typography.titleLarge)

            Divider()

            Text("Perfil", modifier = Modifier.clickable {
                navController.navigate(AppScreen.Perfil.route)
            })


            Text("Pedidos", modifier = Modifier.clickable {
                navController.navigate(AppScreen.Pedidos.route)
            })


            Text("Cambiar contraseña", modifier = Modifier.clickable {
                 navController.navigate(AppScreen.Cambiarcontrasena.route)
            })
            Text("Ajustes", modifier = Modifier.clickable {
                navController.navigate(AppScreen.Settings.route)
            })

            if (authViewModel.isAdmin.collectAsState().value) {
                Divider()
                Text("ADMIN", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(8.dp))

                Text("Gestión de productos", modifier = Modifier.clickable {
                    navController.navigate(AppScreen.AdminProductos.route)
                })

                Text("Gestión de pedidos", modifier = Modifier.clickable {
                    navController.navigate(AppScreen.AdminPedidos.route)
                })

                Text("Gestión de usuarios", modifier = Modifier.clickable {
                    navController.navigate(AppScreen.AdminUsuarios.route)
                })

                Text("Dashboard", modifier = Modifier.clickable {
                    navController.navigate(AppScreen.AdminDashboard.route)
                })
            }


            Text("Cerrar sesión", modifier = Modifier.clickable {
                authViewModel.logout()
                navController.navigate(AppScreen.Login.route) {
                    popUpTo(0) // Elimina el backstack
                }
            }, color = MaterialTheme.colorScheme.error)
        }
    }
}
