package com.example.tfg.ui.viewModel.Factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tfg.ui.viewModel.AuthViewModel
import com.example.tfg.ui.viewModel.PedidoViewModel

class AuthViewModelFactory(
    private val context: Context,
    private val pedidoViewModel: PedidoViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(context, pedidoViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
