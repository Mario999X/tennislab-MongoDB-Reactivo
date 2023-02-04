package dto

import kotlinx.serialization.Serializable
import models.Adquisicion

@Serializable
data class AdquisicionDto(
    var cantidad: String,
    var productoDto: ProductoDto,
    var precio: String
) {
}

fun Adquisicion.toAdquisicionDto(): AdquisicionDto {
    return AdquisicionDto(
        cantidad = cantidad.toString(),
        productoDto = producto.toProductoDto(),
        precio = precio.toString()
    )
}