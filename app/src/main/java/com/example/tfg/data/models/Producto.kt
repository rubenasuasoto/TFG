package com.example.tfg.data.models

data class Producto(
    val id: String? = null,
    val numeroProducto: String,
    val articulo: String? = null,
    val descripcion: String? = null,
    val precio: Double,
    val stock: Int,
    val imagenUrl: String? = null,
    val fechaCreacion: String? = null,
    val fechaActualizacion: String? = null
)
data class ProductoDTO(
    val numeroProducto: String,
    val articulo: String,
    val precio: Double
)
