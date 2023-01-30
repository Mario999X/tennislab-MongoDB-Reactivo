package dto

import kotlinx.serialization.Serializable
import models.Pedido

@Serializable
data class PedidoDto(
    val id: String,
    val estadoPedido: String,
    val fechaEntrada: String,
    val fechaProgramada: String,
    val fechaSalida: String?
)

fun Pedido.toPedidoDto(): PedidoDto {
    return PedidoDto(
        id.toString(),
        estadoPedido.toString(),
        fechaEntrada, fechaProgramada, fechaSalida
    )
}
