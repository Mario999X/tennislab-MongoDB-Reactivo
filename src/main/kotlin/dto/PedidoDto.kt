package dto

import kotlinx.serialization.Serializable
import models.Pedido

@Serializable
data class PedidoDto(
    val estadoPedido: String,
    val fechaEntrada: String,
    val fechaProgramada: String,
    val fechaSalida: String?
)

fun Pedido.toPedidoDto(): PedidoDto {
    return PedidoDto(
        estadoPedido.toString(),
        fechaEntrada, fechaProgramada, fechaSalida
    )
}
