package com.example.tfg.models

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
    val rol: String?
)
data class UsuarioUpdateDTO(
    val currentPassword: String?, // Solo necesario para cambios de contraseña en self
    val newPassword: String?,
    val email: String?,
    val rol: String?, // Solo editable por admin
    val direccion: Direccion?
)
data class Direccion(
    val calle: String,
    val num: String,
    val cp: String,
    val ciudad: String
)