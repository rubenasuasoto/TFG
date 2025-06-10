package com.example.tfg.data.models

data class Usuario(
    val username: String,
    val email: String,
    val password: String,
    val passwordRepeat: String,
    val rol: String,
    val direccion: Direccion
)

data class UsuarioDTO(
    val username: String,
    val email: String,
    val rol: String?,
    val direccion: Direccion
)
data class UsuarioUpdateDTO(
    val currentPassword: String?, // Solo necesario para cambios de contraseña en self
    val newPassword: String?,
    val email: String?,
    val rol: String?, // Solo editable por admin
    val direccion: Direccion
)
data class Direccion(
    val calle: String,
    val num: String,
    val municipio: String,
    val provincia: String,
    val cp: String
)