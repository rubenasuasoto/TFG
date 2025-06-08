package com.example.tfg.models

data class AuthResponse(
    val message: String,
    val token: String?,
    val user: UserDto?
)

data class UserDto(
    val username: String,
    val email: String,
    val roles: String
)
data class LoginRequest(
    val username: String,
    val password: String
)