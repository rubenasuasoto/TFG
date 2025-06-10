package com.example.tfg.data.repository

import com.example.tfg.data.models.UsuarioDTO
import com.example.tfg.data.remote.RetrofitClient

class UserRepository {
    suspend fun getUser(): UsuarioDTO {
        return RetrofitClient.apiService.getSelf()
    }
}