package com.example.tfg.models

import java.util.Date

data class Pedido(

    val numeroPedido: String?,
    val numeroProducto: String,
    val usuario: String?,
    var articulo: String?,
    var precioFinal: Double,
    var factura: Factura,
    var estado: String,
    val fechaCreacion: Date
)
data class PedidoDTO(
    val numeroProducto: String
)
data class Factura(

    val numeroFactura: String,
    val fecha: Date
)