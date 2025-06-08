package com.example.tfg.models

data class Producto(
    val id: String? = null,
    val numeroProducto: String,
    val articulo: String? = null,
    val descripcion: String? = null,
    val precio: Double,
    val stock: Int,
    val imagenUrl: String? = null,
    val fechaCreacion: String? = null,      // ISO 8601 formato desde el backend
    val fechaActualizacion: String? = null
)