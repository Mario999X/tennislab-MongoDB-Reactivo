package dto

import kotlinx.serialization.Serializable
import models.Adquisicion

@Serializable
data class AdquisicionDto(
    val id: String,
    var uuid: String,
    var cantidad: String,
    var productoDto: ProductoDto,
    var precio: String
) {
}

fun Adquisicion.toAdquisicionDto(): AdquisicionDto {
    return AdquisicionDto(
        id = id.toString(),
        uuid = uuid.toString(),
        cantidad = cantidad.toString(),
        productoDto = producto.toProductoDto(),
        precio = precio.toString()
    )
}