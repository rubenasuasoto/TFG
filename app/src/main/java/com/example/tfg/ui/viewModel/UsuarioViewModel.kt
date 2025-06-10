package com.example.tfg.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.data.models.UsuarioDTO
import com.example.tfg.data.models.UsuarioUpdateDTO
import com.example.tfg.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch




class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val _usuario = MutableStateFlow<UsuarioDTO?>(null)
    val usuario: StateFlow<UsuarioDTO?> = _usuario

    fun fetchSelf() {
        viewModelScope.launch {
            try {
                _usuario.value = RetrofitClient.apiService.getSelf()
            } catch (e: Exception) {
                Log.e("UsuarioVM", "Error al obtener usuario", e)
            }
        }
    }

    fun updateSelf(dto: UsuarioUpdateDTO, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.updateSelf(dto)
                onResult(response.isSuccessful)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }
}