package com.example.tfg.viewModel.Factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tfg.viewModel.AuthViewModel
import com.example.tfg.viewModel.PedidoViewModel

class AuthViewModelFactory(
    private val context: Context,
    private val pedidoViewModel: PedidoViewModel  // 🔹 Recibir TareaViewModel como dependencia
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(context, pedidoViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
