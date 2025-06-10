package com.example.tfg.ui.viewModel.Factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tfg.ui.viewModel.ProductoViewModel

class ProductoViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
