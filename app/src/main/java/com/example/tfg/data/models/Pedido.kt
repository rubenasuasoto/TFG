package com.example.tfg.data.models

import java.util.Date

data class Pedido(
    val numeroPedido: String?,
    val productos: List<String>,
    val usuario: String?,
    val detalles: List<ProductoDTO>,
    val precioFinal: Double,
    val factura: Factura,
    val estado: String,
    val fechaCreacion: Date
)
data class PedidoDTO(
    val productos: List<String>,
    val precioFinal: Double
)

data class Factura(

    val numeroFactura: String,
    val fecha: Date
)