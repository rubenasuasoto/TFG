package com.example.tfg.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.data.models.Direccion
import com.example.tfg.data.models.LoginRequest
import com.example.tfg.data.models.Usuario
import com.example.tfg.data.models.UsuarioDTO
import com.example.tfg.data.models.UsuarioUpdateDTO
import com.example.tfg.data.remote.RetrofitClient
import com.example.tfg.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class AuthViewModel(
    private val context: Context,
    private val pedidoViewModel: PedidoViewModel
) : ViewModel() {

    // ░░░ Estados ░░░
    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState

    private val _isAdmin = MutableStateFlow(false)
    val isAdmin: StateFlow<Boolean> = _isAdmin

    private val _usuarioActual = MutableStateFlow<UsuarioDTO?>(null)
    val usuarioActual: StateFlow<UsuarioDTO?> = _usuarioActual

    private val _listaUsuarios = MutableStateFlow<List<UsuarioDTO>>(emptyList())
    val listaUsuarios: StateFlow<List<UsuarioDTO>> = _listaUsuarios

    // ░░░ Estados de autenticación ░░░
    sealed class AuthState {
        data object Idle : AuthState()
        data object Loading : AuthState()
        data class Success(val token: String, val isAdmin: Boolean) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    // ░░░ Login y registro ░░░
    fun login(username: String, password: String) {
        _loginState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.login(LoginRequest(username, password))
                if (response.token != null) {
                    RetrofitClient.updateToken(response.token, context)
                    Log.d("AuthViewModel", "Token guardado: ${TokenManager.getToken(context)}")
                    val isAdmin = checkAdminStatus()
                    _loginState.value = AuthState.Success(response.token, isAdmin)
                } else {
                    _loginState.value = AuthState.Error("Token no recibido")
                }
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(
                    when (e) {
                        is IOException -> "Error de conexión"
                        is HttpException -> "Credenciales incorrectas"
                        else -> "Error desconocido"
                    }
                )
            }
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        passwordRepeat: String,
        direccion: Direccion,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.register(
                    Usuario(
                        username = username,
                        email = email,
                        password = password,
                        passwordRepeat = passwordRepeat,
                        rol = "USER",
                        direccion = direccion
                    )
                )
                onResult(true, "Registro exitoso")
            } catch (e: HttpException) {
                onResult(false, if (e.code() == 409) "Usuario/email ya existe" else "Error en el servidor")
            } catch (e: Exception) {
                onResult(false, "Error de conexión")
            }
        }
    }

    // ░░░ Perfil del usuario ░░░
    fun cargarPerfil() {
        viewModelScope.launch {
            try {
                val usuario = RetrofitClient.apiService.getSelf()
                _usuarioActual.value = usuario
            } catch (e: Exception) {
                Log.e("AuthViewModel", " Error al cargar perfil", e)
                _usuarioActual.value = null
            }
        }
    }

    fun actualizarPerfil(dto: UsuarioUpdateDTO, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.updateSelf(dto)
                if (response.isSuccessful) {
                    onResult(true, " Perfil actualizado")
                } else {
                    onResult(false, " No se pudo actualizar el perfil")
                }
            } catch (e: Exception) {
                onResult(false, " Error de red o servidor")
            }
        }
    }

    fun cambiarPassword(
        currentPassword: String,
        newPassword: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val dto = UsuarioUpdateDTO(
                    currentPassword = currentPassword,
                    newPassword = newPassword,
                    email = null,
                    rol = null,
                    direccion = null
                )

                val response = RetrofitClient.apiService.updateSelf(dto)
                if (response.isSuccessful) {
                    onResult(true, " Contraseña actualizada correctamente")
                } else {
                    onResult(false, " No se pudo actualizar la contraseña")
                }
            } catch (e: Exception) {
                onResult(false, " Error de red o servidor")
            }
        }
    }

    // ░░░ Token persistente ░░░
    fun checkExistingToken() {
        val savedToken = TokenManager.getToken(context)
        Log.d("AuthViewModel", " checkExistingToken() → $savedToken")

        if (!savedToken.isNullOrEmpty()) {
            RetrofitClient.updateToken(savedToken, context)
            viewModelScope.launch {
                val isAdmin = checkAdminStatus()
                _loginState.value = AuthState.Success(savedToken, isAdmin)
                pedidoViewModel.cargarCarrito()
            }
        }
    }

    fun logout() {
        TokenManager.clearToken(context)
        _loginState.value = AuthState.Idle
    }

    // ░░░ Admin ░░░
    private suspend fun checkAdminStatus(): Boolean {
        return try {
            val response = RetrofitClient.apiService.checkAdmin()
            val isAdmin = response.isSuccessful && response.body()?.get("isAdmin") == true
            _isAdmin.value = isAdmin
            pedidoViewModel.setAdminStatus(isAdmin)
            isAdmin
        } catch (e: Exception) {
            _isAdmin.value = false
            pedidoViewModel.setAdminStatus(false)
            false
        }
    }

    fun fetchAllUsuarios() {
        viewModelScope.launch {
            try {
                val usuarios = RetrofitClient.apiService.getAll()
                _listaUsuarios.value = usuarios
            } catch (e: Exception) {
                Log.e("AuthViewModel", " Error al obtener usuarios", e)
            }
        }
    }
}
